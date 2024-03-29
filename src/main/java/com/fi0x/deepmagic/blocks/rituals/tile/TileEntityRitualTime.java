package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.blocks.rituals.RITUAL_TYPE;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public class TileEntityRitualTime extends TileEntityRitualStone
{
    public TileEntityRitualTime()
    {
        type = RITUAL_TYPE.TIME;
        manaCosts = ConfigHandler.ritualTimeManaCosts;
    }

    private long time = 0;

    @Override
    public String getPacketData()
    {
        //TODO
        return null;
    }

    @Override
    public void setDataFromPacket(String parts)
    {
        //TODO
    }

    @Override
    public void syncedUpdate()
    {
        //TODO Check if packet is required
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