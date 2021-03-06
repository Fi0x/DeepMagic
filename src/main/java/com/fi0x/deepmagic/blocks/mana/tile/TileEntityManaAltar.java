package com.fi0x.deepmagic.blocks.mana.tile;

import com.fi0x.deepmagic.util.IManaTileEntity;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;

public class TileEntityManaAltar extends TileEntity implements IInventory, ITickable, IManaTileEntity
{
    private NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    private String customName;

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
        return isItemChargeable(stack);
    }
    @Override
    public int getField(int id)
    {
        return id == 0 ? storedMana : 0;
    }
    @Override
    public void setField(int id, int value)
    {
        if(id == 0) storedMana = value;
    }
    @Override
    public int getFieldCount()
    {
        return 1;
    }
    @Override
    public void clear()
    {
        inventory.clear();
    }
    @Override
    public void update()
    {
        boolean dirty = false;
        int manaRemove = 0;

        if(!inventory.get(0).isEmpty())
        {
            manaRemove = Math.min(storedMana, 20);
            storedMana -= manaRemove;
            dirty = true;
        }

        if(world.isRemote) return;

        ItemStack stack = inventory.get(0);
        if(!stack.isEmpty())
        {
            NBTTagCompound compound;
            if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
            compound = stack.getTagCompound();
            assert compound != null;

            int manaInItem = 0;
            if(compound.hasKey("manaCharge")) manaInItem = compound.getInteger("manaCharge");
            manaInItem += manaRemove;
            compound.setInteger("manaCharge", manaInItem);
            dirty = true;
        }
        if(dirty) markDirty();
    }
    @Nonnull
    @Override
    public String getName()
    {
        return hasCustomName() ? customName : "container.mana_altar";
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
        compound.setInteger("storedMana", storedMana);
        ItemStackHelper.saveAllItems(compound, inventory);
        if(hasCustomName()) compound.setString("customName", customName);
        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        storedMana = compound.getInteger("storedMana");
        inventory = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inventory);
        if(compound.hasKey("customName")) setCustomName(compound.getString("customName"));
        super.readFromNBT(compound);
    }
    @Override
    public double getSpaceForMana()
    {
        return ConfigHandler.manaAltarCapacity - storedMana;
    }
    @Override
    public double addManaToStorage(double amount)
    {
        double ret = amount - (ConfigHandler.manaAltarCapacity - storedMana);
        if(ret > 0) storedMana = ConfigHandler.manaAltarCapacity;
        else storedMana += amount;
        markDirty();
        return ret > 0 ? ret : 0;
    }
    public void setCustomName(String customName)
    {
        this.customName = customName;
    }
    public static boolean isItemChargeable(ItemStack input)
    {
        if(!input.isEmpty())
        {
            return false;
        }
        return false;
    }
    public double getStoredMana()
    {
        return storedMana;
    }
    public double getSpaceInAltar()
    {
        return ConfigHandler.manaAltarCapacity - storedMana;
    }
}
