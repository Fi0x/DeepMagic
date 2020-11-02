package com.fi0x.deepmagic.entities.tileentity;

import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

public class TileEntityManaAltar extends TileEntity
{
    private double storedMana;

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setDouble("storedMana", storedMana);
        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        storedMana = compound.getDouble("storedMana");
        super.readFromNBT(compound);
    }

    public double getStoredMana()
    {
        return storedMana;
    }
    public boolean addManaToStorage(double amount)
    {
        if(storedMana + amount > ConfigHandler.manaAltarCapacity && storedMana < ConfigHandler.manaAltarCapacity) storedMana = ConfigHandler.manaAltarCapacity;
        else if(storedMana + amount <= ConfigHandler.manaAltarCapacity) storedMana += amount;
        else return false;
        markDirty();
        return true;
    }
    public boolean removeManaFromStorage(double amount)
    {
        if(storedMana - amount < 0) return false;
        storedMana -= amount;
        markDirty();
        return true;
    }
}