package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public class TileEntityRitualWeather extends TileEntityRitualStone
{
    public TileEntityRitualWeather()
    {
        manaCosts = ConfigHandler.ritualTimeManaCosts;
    }

    private WEATHER weather = WEATHER.RAIN;
    @Override
    protected void syncedUpdate()
    {
        //TODO: Change weather
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        compound.setInteger("weather", weather.ordinal());

        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        weather = WEATHER.values()[compound.getInteger("weather")];

        super.readFromNBT(compound);
    }

    public String nextWeather()
    {
        switch(weather)
        {
            case SUN:
                weather = WEATHER.RAIN;
                break;
            case RAIN:
                weather = WEATHER.STORM;
                break;
            case STORM:
                weather = WEATHER.SUN;
                break;
        }

        return weather.toString();
    }

    enum WEATHER
    {
        SUN,
        RAIN,
        STORM
    }
}