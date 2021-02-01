package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nonnull;

public class TileEntityRitualWeather extends TileEntityRitualStone
{
    private WEATHER weather = WEATHER.Raining;

    public TileEntityRitualWeather()
    {
        manaCosts = ConfigHandler.ritualWeatherManaCosts;
        syncTime = 100;
    }

    @Override
    protected void syncedUpdate()
    {
        WorldInfo info = world.getWorldInfo();
        switch(weather)
        {
            case Sunny:
                if(info.isRaining() || info.isThundering())
                {
                    if(storedMana >= manaCosts)
                    {
                        storedMana -= manaCosts;
                        info.setRaining(false);
                        info.setThundering(false);
                    }
                }
                break;
            case Raining:
                if(!info.isRaining() || info.isThundering())
                {
                    if(storedMana >= manaCosts)
                    {
                        storedMana -= manaCosts;
                        info.setRaining(true);
                        info.setThundering(false);
                    }
                }
                break;
            case Stormy:
                if(!info.isThundering())
                {
                    if(storedMana >= manaCosts)
                    {
                        storedMana -= manaCosts;
                        info.setThundering(true);
                    }
                }
                break;
        }
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
        weather = WEATHER.values()[(weather.ordinal() + 1) % WEATHER.values().length];
        return weather.toString();
    }

    enum WEATHER
    {
        Sunny,
        Raining,
        Stormy
    }
}