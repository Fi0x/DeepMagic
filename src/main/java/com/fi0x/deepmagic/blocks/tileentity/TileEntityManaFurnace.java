package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.blocks.mana.ManaFurnace;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;

public class TileEntityManaFurnace extends TileEntity implements IInventory, ITickable
{
    private NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);
    private String customName;

    private BlockPos linkedAltarPos;
    private TileEntityManaAltar linkedAltar;
    private int smeltProgress;
    private int totalSmeltTime;
    private int storedMana;

    @Override
    public int getSizeInventory()
    {
        return inventory.size();
    }
    @Override
    public boolean isEmpty()
    {
        for(ItemStack stack : inventory)
        {
            if(!stack.isEmpty()) return false;
        }
        return true;
    }
    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index)
    {
        return inventory.get(index);
    }
    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(inventory, index, count);
    }
    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(inventory, index);
    }
    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack)
    {
        inventory.set(index, stack);

        if(stack.getCount() > getInventoryStackLimit()) stack.setCount(getInventoryStackLimit());
    }
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }
    @Override
    public boolean isUsableByPlayer(@Nonnull EntityPlayer player)
    {
        return world.getTileEntity(pos) == this && player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64;
    }
    @Override
    public void openInventory(@Nonnull EntityPlayer player)
    {
    }
    @Override
    public void closeInventory(@Nonnull EntityPlayer player)
    {
    }
    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack)
    {
        return index != 1;
    }
    @Override
    public int getField(int id)
    {
        switch (id)
        {
            case 0: return smeltProgress;
            case 1: return totalSmeltTime;
            case 2: return storedMana;
        }
        return 0;
    }
    @Override
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0: smeltProgress = value;
            break;
            case 1: totalSmeltTime = value;
            break;
            case 2: storedMana = value;
            break;
        }
    }
    @Override
    public int getFieldCount()
    {
        return 3;
    }
    @Override
    public void clear()
    {
        inventory.clear();
    }
    @Override
    public void update()
    {
        boolean wasRunning = isRunning();
        boolean dirty = false;

        if(!world.isRemote)
        {
            ItemStack stack = inventory.get(0);
            if(stack.isEmpty())
            {
                if(totalSmeltTime > 0)
                {
                    smeltProgress = 0;
                    totalSmeltTime = 0;
                    dirty = true;
                }
            } else if (storedMana > 0)
            {
                if(canSmelt())
                {
                    storedMana--;
                    smeltProgress++;
                    dirty = true;
                    if(totalSmeltTime == 0) totalSmeltTime = getItemSmeltTime();
                    if (smeltProgress >= totalSmeltTime)
                    {
                        smeltProgress = 0;
                        smeltItem();
                        totalSmeltTime = getItemSmeltTime();
                    }
                } else smeltProgress = 0;
            }

            if(isRunning() != wasRunning)
            {
                ManaFurnace.setState(isRunning(), world, pos);
                dirty = true;
            }
            if(ConfigHandler.manaMachineManaCapacity - storedMana >= 10)
            {
                if(getManaFromAltar()) dirty = true;
            }
        }
        if(dirty) markDirty();
    }
    @Nonnull
    @Override
    public String getName()
    {
        return hasCustomName() ? customName : "container.mana_furnace";
    }
    @Override
    public boolean hasCustomName()
    {
        return customName != null && !customName.isEmpty();
    }
    @Nonnull
    @Override
    public ITextComponent getDisplayName()
    {
        return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName());
    }
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        if(linkedAltarPos != null)
        {
            compound.setInteger("altarX", linkedAltarPos.getX());
            compound.setInteger("altarY", linkedAltarPos.getY());
            compound.setInteger("altarZ", linkedAltarPos.getZ());
            compound.setBoolean("linked", true);
        } else compound.setBoolean("linked", false);

        compound.setInteger("smeltProgress", smeltProgress);
        compound.setInteger("storedMana", storedMana);
        ItemStackHelper.saveAllItems(compound, inventory);
        if(hasCustomName()) compound.setString("customName", customName);
        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        if(compound.hasKey("linked") && compound.getBoolean("linked"))
        {
            int x = compound.getInteger("altarX");
            int y = compound.getInteger("altarY");
            int z = compound.getInteger("altarZ");
            linkedAltarPos = new BlockPos(x, y, z);
        }

        smeltProgress = compound.getInteger("smeltProgress");
        totalSmeltTime = getItemSmeltTime();
        storedMana = compound.getInteger("storedMana");
        inventory = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inventory);
        if(compound.hasKey("customName")) setCustomName(compound.getString("customName"));
        super.readFromNBT(compound);
    }
    public void setCustomName(String customName)
    {
        this.customName = customName;
    }
    public boolean isRunning()
    {
        return smeltProgress > 0;
    }
    private boolean canSmelt()
    {
        ItemStack inputStack = inventory.get(0);
        if (inputStack.isEmpty()) return false;
        else
        {
            ItemStack furnaceResult = FurnaceRecipes.instance().getSmeltingResult(inputStack);
            if (furnaceResult.isEmpty()) return false;

            ItemStack output = inventory.get(1);
            if (output.isEmpty()) return true;
            if (!output.isItemEqual(furnaceResult)) return false;

            return output.getCount() + furnaceResult.getCount() <= getInventoryStackLimit();
        }
    }
    public static int getItemSmeltTime()
    {
        return 100;
    }
    public static boolean isItemSmeltable()
    {
        return getItemSmeltTime() > 0;
    }
    private void smeltItem()
    {
        ItemStack input = inventory.get(0);
        ItemStack result = FurnaceRecipes.instance().getSmeltingResult(input);
        if(!result.isEmpty())
        {
            ItemStack output1 = inventory.get(1);

            if(output1.getItem() == result.getItem()) output1.grow(result.getCount());
            else if(output1.isEmpty()) inventory.set(1, result);

            input.shrink(1);
        }
    }
    public void setLinkedAltarPos(BlockPos blockPos)
    {
        linkedAltarPos = blockPos;
        if(linkedAltarPos == null) linkedAltar = null;
        else linkedAltar = (TileEntityManaAltar) world.getTileEntity(linkedAltarPos);
    }
    private boolean getManaFromAltar()
    {
        if(!ManaHelper.isAltarValid(world, pos, linkedAltarPos, linkedAltar)) return false;
        linkedAltar = (TileEntityManaAltar) world.getTileEntity(linkedAltarPos);

        assert linkedAltar != null;
        if(linkedAltar.getStoredMana() > 10)
        {
            if(linkedAltar.removeManaFromStorage(10)) storedMana += 10;
        }
        return true;
    }
}
