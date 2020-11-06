package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.blocks.mana.ManaGenerator;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;

public class TileEntityManaInfuser extends TileEntity implements IInventory, ITickable
{
    private NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);
    private String customName;

    private BlockPos linkedAltarPos;
    private TileEntityManaAltar linkedAltar;
    private int infusionProgress;
    private int totalInsusionTime;
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
        if(index == 1) return false;
        return isItemInfusable(stack);
    }
    @Override
    public int getField(int id)
    {
        switch (id)
        {
            case 0: return infusionProgress;
            case 1: return totalInsusionTime;
            case 2: return storedMana;
        }
        return 0;
    }
    @Override
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0: infusionProgress = value;
            break;
            case 1: totalInsusionTime = value;
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
            if (storedMana > 0 && !stack.isEmpty())
            {
                if (canInfuse())
                {
                    infusionProgress++;

                    if (infusionProgress == totalInsusionTime)
                    {
                        infusionProgress = 0;
                        totalInsusionTime = getItemInfusionTime(stack);
                        infuseItem();
                        dirty = true;
                    }
                } else infusionProgress = 0;
            }

            if(isRunning() != wasRunning)
            {
                ManaGenerator.setState(isRunning(), world, pos);
                dirty = true;
            }
            if(ConfigHandler.manaInfuserManaCapacity - storedMana >= 10)
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
        return hasCustomName() ? customName : "container.mana_infuser";
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

        compound.setInteger("infusionProgress", infusionProgress);
        compound.setInteger("storedMana", storedMana);
        ItemStackHelper.saveAllItems(compound, inventory);
        if(hasCustomName()) compound.setString("customName", customName);
        return super.writeToNBT(compound);// TODO fix "missing mapping" error
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

        infusionProgress = compound.getInteger("infusionProgress");
        totalInsusionTime = getItemInfusionTime(inventory.get(0));
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
        return infusionProgress > 0;
    }
    private boolean canInfuse()
    {
        if (inventory.get(0).isEmpty()) return false;
        else
        {
            ItemStack infusionResult = getInfusionResult(inventory.get(0));

            if (infusionResult.isEmpty()) return false;
            else
            {
                ItemStack output = inventory.get(1);

                if (output.isEmpty()) return true;
                else if (!output.isItemEqual(infusionResult)) return false;
                else if (output.getCount() + infusionResult.getCount() <= getInventoryStackLimit() && output.getCount() + infusionResult.getCount() <= output.getMaxStackSize()) return true;
                else return false;
            }
        }
    }
    public static int getItemInfusionTime(ItemStack infusionStack)
    {
        if(infusionStack.isEmpty()) return 0;

        Item item = infusionStack.getItem();
        if(item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR)
        {
            Block block = Block.getBlockFromItem(item);
            if(block == ModBlocks.DEEP_CRYSTAL_BLOCK) return 100;
        } else if(item == ModItems.DEEP_CRYSTAL_POWDER) return 100;

        return 0;
    }
    public static boolean isItemInfusable(ItemStack item)
    {
        return getItemInfusionTime(item) > 0;
    }
    public static ItemStack getInfusionResult(ItemStack input)
    {
        Item item = input.getItem();
        ItemStack result = ItemStack.EMPTY;
        if(item instanceof ItemBlock)
        {
            Block block = Block.getBlockFromItem(item);
            if(block == ModBlocks.DEEP_CRYSTAL_BLOCK) result = new ItemStack(ModBlocks.DEMON_CRYSTAL_BLOCK, 1);
        } else if(item == ModItems.DEEP_CRYSTAL_POWDER) result = new ItemStack(ModItems.MAGIC_POWDER, 1);
        return result;
    }
    private void infuseItem()
    {
        ItemStack input = inventory.get(0);
        ItemStack result = getInfusionResult(input);
        if(!result.isEmpty())
        {
            ItemStack output = inventory.get(1);

            if(output.isEmpty()) inventory.set(1, result);
            else if(output.getItem() == result.getItem()) output.grow(result.getCount());

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
        if(linkedAltarPos == null) return false;

        if(linkedAltar == null)
        {
            linkedAltar = (TileEntityManaAltar) world.getTileEntity(linkedAltarPos);
            if(linkedAltar == null)
            {
                linkedAltarPos = null;
                return true;
            }
        }
        if(linkedAltar.getStoredMana() > 10)
        {
            if(linkedAltar.removeManaFromStorage(10)) storedMana += 10;
        }
        return true;
    }
}
