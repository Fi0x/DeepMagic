package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.util.IManaTileEntity;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import javax.annotation.Nonnull;

public abstract class TileEntityRitualStone extends TileEntity implements ITickable, IManaTileEntity
{
    private double storedMana;

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        compound.setDouble("stored", storedMana);

        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        storedMana = compound.getDouble("stored");

        super.readFromNBT(compound);
    }

    @Override
    public void update()
    {
        /*
        TODO: Verify structure
         Remove Mana
         Perform action in child-class
         */
    }

    @Override
    public double getSpaceForMana()
    {
        return ConfigHandler.ritualStoneManaCapacity - storedMana;
    }
    @Override
    public double addManaToStorage(double amount)
    {
        double ret = amount - (ConfigHandler.ritualStoneManaCapacity - storedMana);
        if(ret > 0) storedMana = ConfigHandler.manaAltarCapacity;
        else storedMana += amount;
        markDirty();
        return ret > 0 ? ret : 0;
    }
}
