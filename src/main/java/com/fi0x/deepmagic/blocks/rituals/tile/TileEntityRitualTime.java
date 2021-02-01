package com.fi0x.deepmagic.blocks.rituals.tile;

import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public class TileEntityRitualTime extends TileEntityRitualStone
{
    private long time = 0;
    @Override
    protected void syncedUpdate()
    {
        world.getWorldInfo().setWorldTime(time);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        compound.setLong("time", time);

        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        time = compound.getLong("time");

        super.readFromNBT(compound);
    }

    public double addTime(double amount)
    {
        time += amount;
        time %= 24000;
        return time;
    }
}