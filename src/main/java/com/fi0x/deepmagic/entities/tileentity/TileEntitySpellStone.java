package com.fi0x.deepmagic.entities.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

public class TileEntitySpellStone extends TileEntity
{
    private int range;
    private int radius;
    private int target;
    private int attackDmg;
    private boolean envDmg;
    private boolean explosive;
    private int heal;
    private int time;
    private boolean weather;

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("range", range);
        compound.setInteger("radius", radius);
        compound.setInteger("target", target);
        compound.setInteger("attackDmg", attackDmg);
        compound.setBoolean("envDmg", envDmg);
        compound.setBoolean("explosive", explosive);
        compound.setInteger("heal", heal);
        compound.setInteger("time", time);
        compound.setBoolean("weather", weather);
        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        range = compound.getInteger("range");
        radius = compound.getInteger("radius");
        target = compound.getInteger("target");
        attackDmg = compound.getInteger("attackDmg");
        envDmg = compound.getBoolean("envDmg");
        explosive = compound.getBoolean("explosive");
        heal = compound.getInteger("heal");
        time = compound.getInteger("time");
        weather = compound.getBoolean("weather");
        super.readFromNBT(compound);
    }

    public int getRange()
    {
        return range;
    }
    public void increaseRange(int x)
    {
        range += x;
        markDirty();
    }
    public int resetRange(int currrentManaCosts)
    {
        int tmp = range;
        range = 0;
        markDirty();
        return currrentManaCosts * tmp;
    }
    public int getRadius()
    {
        return range;
    }
    public void setRadius(int x)
    {
        radius = x;
        markDirty();
    }
    public int resetRadius(int currrentManaCosts)
    {
        int tmp = radius;
        radius = 0;
        markDirty();
        return currrentManaCosts * tmp;
    }
    public int getTarget()
    {
        return target;
    }
    public void setTarget(int x)
    {
        target = x;
        markDirty();
    }
    public int resetTarget(int currrentManaCosts)
    {
        int tmp = target;
        target = 0;
        markDirty();
        return currrentManaCosts * tmp;
    }
    public int getAttackDmg()
    {
        return attackDmg;
    }
    public void setAttackDmg(int x)
    {
        attackDmg = x;
        markDirty();
    }
    public int resetAttackDmg(int currentManaCosts)
    {
        int tmp = attackDmg;
        attackDmg = 0;
        markDirty();
        return currentManaCosts * tmp;
    }
    public boolean doesEnvDmg()
    {
        return envDmg;
    }
    public void setEnvDmg()
    {
        envDmg = true;
        markDirty();
    }
    public int resetEnvDmg(int currrentManaCosts)
    {
        envDmg = false;
        markDirty();
        return currrentManaCosts * 2;
    }
    public boolean isExplosive()
    {
        return explosive;
    }
    public void setExplosive()
    {
        explosive = true;
        markDirty();
    }
    public int resetExplosive(int currrentManaCosts)
    {
        explosive = false;
        markDirty();
        return currrentManaCosts * 2;
    }
    public int getHeal()
    {
        return target;
    }
    public void setHeal(int x)
    {
        heal = x;
        markDirty();
    }
    public int resetHeal(int currrentManaCosts)
    {
        int tmp = heal;
        heal = 0;
        markDirty();
        return currrentManaCosts * tmp;
    }
    public int getTime()
    {
        return time;
    }
    public void setTime(int x)
    {
        time = x;
        markDirty();
    }
    public int resetTime(int currrentManaCosts)
    {
        int tmp = time;
        time = 0;
        markDirty();
        return currrentManaCosts * tmp;
    }
    public boolean doesWeather()
    {
        return weather;
    }
    public void setWeather()
    {
        weather = true;
        markDirty();
    }
    public int resetWeather(int currrentManaCosts)
    {
        weather = false;
        markDirty();
        return currrentManaCosts * 2;
    }
}
