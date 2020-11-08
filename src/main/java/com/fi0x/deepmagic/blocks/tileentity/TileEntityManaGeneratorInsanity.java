package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.blocks.mana.ManaGeneratorInsanity;
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

public class TileEntityManaGeneratorInsanity extends TileEntity implements IInventory, ITickable
{
    private NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    private String customName;

    private BlockPos linkedAltarPos;
    private TileEntityManaAltar linkedAltar;
    private int burnTime;
    private int currentBurnTime;
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
        return isItemFuel(stack) || index != 0;
    }
    @Override
    public int getField(int id)
    {
        switch (id)
        {
            case 0: return burnTime;
            case 1: return currentBurnTime;
            case 2: return storedMana;
        }
        return 0;
    }
    @Override
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0: burnTime = value;
            break;
            case 1: currentBurnTime = value;
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
        if(isRunning())
        {
            burnTime--;
            if(storedMana - 10 < ConfigHandler.manaGeneratorManaCapacity) storedMana += 10;
            dirty = true;
        }
        if(world.isRemote) return;

        ItemStack stack = inventory.get(0);

        if(!isRunning() && storedMana < ConfigHandler.manaGeneratorManaCapacity)
        {
            if(!stack.isEmpty())
            {
                burnTime = getItemBurnTime(stack);
                currentBurnTime = burnTime;
                stack.shrink(1);
                inventory.set(0, stack);
                dirty = true;
            }
        }
        if(isRunning() != wasRunning) ManaGeneratorInsanity.setState(isRunning(), world, pos);
        if(storedMana >= 100)
        {
            if(sendManaToAltar()) dirty = true;
        }
        if(dirty) markDirty();
    }
    @Nonnull
    @Override
    public String getName()
    {
        return hasCustomName() ? customName : "container.mana_generator_insanity";
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

        compound.setInteger("burnTime", burnTime);
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

        burnTime = compound.getInteger("burnTime");
        currentBurnTime = getItemBurnTime(inventory.get(0));
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
        return burnTime > 0;
    }
    public static int getItemBurnTime(ItemStack fuel)
    {
        if(fuel.isEmpty()) return 0;

        Item item = fuel.getItem();
        if(item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR)
        {
            Block block = Block.getBlockFromItem(item);
            if(block == ModBlocks.INSANITY_STONE) return 50;
            if(block == ModBlocks.INSANITY_COBBLE) return 10;
            if(block == ModBlocks.INSANITY_DIRT) return 10;
            if(block == ModBlocks.INSANITY_GRASS) return 25;
            if(block == ModBlocks.INSANITY_PLANKS) return 10;
            if(block == ModBlocks.INSANITY_FLOWER) return 30;
            if(block == ModBlocks.INSANITY_LOG) return 30;
            if(block == ModBlocks.INSANITY_SAPLING) return 10;
        } else
        {
            if(item == ModItems.INSANITY_APPLE) return 100;
        }
        if(item.getUnlocalizedName().contains("insanity")) return 20;
        return 0;
    }
    public static boolean isItemFuel(ItemStack fuel)
    {
        return getItemBurnTime(fuel) > 0;
    }
    public void setLinkedAltarPos(BlockPos blockPos)
    {
        linkedAltarPos = blockPos;
        if(linkedAltarPos == null) linkedAltar = null;
        else linkedAltar = (TileEntityManaAltar) world.getTileEntity(linkedAltarPos);
    }
    private boolean sendManaToAltar()
    {
        if(!ManaHelper.isAltarValid(world, pos, linkedAltarPos, linkedAltar)) return false;
        linkedAltar = (TileEntityManaAltar) world.getTileEntity(linkedAltarPos);

        int spaceInAltar = (int) linkedAltar.getSpaceInAltar();
        if(spaceInAltar > storedMana)
        {
            if(linkedAltar.addManaToStorage(storedMana)) storedMana = 0;
        } else
        {
            if(linkedAltar.addManaToStorage(spaceInAltar)) storedMana -= spaceInAltar;
        }
        return true;
    }
}
