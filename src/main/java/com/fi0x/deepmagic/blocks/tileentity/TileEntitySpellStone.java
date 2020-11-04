package com.fi0x.deepmagic.blocks.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

public class TileEntitySpellStone extends TileEntity
{
    private int range;
    private int radius;
    private boolean targetSelf;
    private boolean targetSelfPos;
    private boolean targetFocus;
    private boolean targetFocusPos;
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
        compound.setBoolean("targetSelf", targetSelf);
        compound.setBoolean("targetSelfPos", targetSelfPos);
        compound.setBoolean("targetFocus", targetFocus);
        compound.setBoolean("targetFocusPos", targetFocusPos);
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
        targetSelf = compound.getBoolean("targetSelf");
        targetSelfPos = compound.getBoolean("targetSelfPos");
        targetFocus = compound.getBoolean("targetFocus");
        targetFocusPos = compound.getBoolean("targetFocusPos");
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
        return radius;
    }
    public void increaseRadius(int x)
    {
        radius += x;
        markDirty();
    }
    public int resetRadius(int currrentManaCosts)
    {
        int tmp = radius;
        radius = 0;
        markDirty();
        return currrentManaCosts * tmp;
    }
    public boolean isTargetSelf()
    {
        return targetSelf;
    }
    public void setTargetSelf()
    {
        targetSelf = true;
        markDirty();
    }
    public int resetTargetSelf(int currrentManaCosts)
    {
        targetSelf = false;
        markDirty();
        return currrentManaCosts * 2;
    }
    public boolean isTargetSelfPos()
    {
        return targetSelfPos;
    }
    public void setTargetSelfPos()
    {
        targetSelfPos = true;
        markDirty();
    }
    public int resetTargetSelfPos(int currrentManaCosts)
    {
        targetSelfPos = false;
        markDirty();
        return currrentManaCosts * 2;
    }
    public boolean isTargetFocus()
    {
        return targetFocus;
    }
    public void setTargetFocus()
    {
        targetFocus = true;
        markDirty();
    }
    public int resetTargetFocus(int currrentManaCosts)
    {
        targetFocus = false;
        markDirty();
        return currrentManaCosts * 2;
    }
    public boolean isTargetFocusPos()
    {
        return targetFocusPos;
    }
    public void setTargetFocusPos()
    {
        targetFocusPos = true;
        markDirty();
    }
    public int resetTargetFocusPos(int currrentManaCosts)
    {
        targetFocusPos = false;
        markDirty();
        return currrentManaCosts * 2;
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
        return heal;
    }
    public void increaseHeal(int x)
    {
        heal += x;
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
    public void addTime(int x)
    {
        time += x;
        markDirty();
    }
    public int resetTime(int currrentManaCosts)
    {
        int tmp = time;
        time = 0;
        markDirty();
        return currrentManaCosts * (tmp / 500);
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
