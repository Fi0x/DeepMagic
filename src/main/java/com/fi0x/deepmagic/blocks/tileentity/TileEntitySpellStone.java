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

    private int manaAdder;
    private double manaMultiplier;

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
    public void resetRange()
    {
        int tmp = range;
        range = 0;
        markDirty();
        manaMultiplier += 1.5 * tmp;
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
    public void resetRadius()
    {
        int tmp = radius;
        radius = 0;
        markDirty();
        manaMultiplier += 2 * tmp;
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
    public void resetTargetSelf()
    {
        targetSelf = false;
        markDirty();
        manaAdder += 10;
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
    public void resetTargetSelfPos()
    {
        targetSelfPos = false;
        markDirty();
        manaAdder += 50;
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
    public void resetTargetFocus()
    {
        targetFocus = false;
        markDirty();
        manaAdder += 50;
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
    public void resetTargetFocusPos()
    {
        targetFocusPos = false;
        markDirty();
        manaAdder += 100;
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
    public void resetAttackDmg()
    {
        int tmp = attackDmg;
        attackDmg = 0;
        markDirty();
        manaAdder += 50 * tmp;
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
    public void resetEnvDmg()
    {
        envDmg = false;
        markDirty();
        manaMultiplier += 1.5;
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
    public void resetExplosive()
    {
        explosive = false;
        markDirty();
        manaMultiplier += 1.5;
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
    public void resetHeal()
    {
        int tmp = heal;
        heal = 0;
        markDirty();
        manaAdder += 100 * tmp;
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
    public void resetTime()
    {
        int tmp = time;
        time = 0;
        markDirty();
        manaAdder += 2 * tmp;
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
    public void resetWeather()
    {
        weather = false;
        markDirty();
        manaMultiplier += 2;
    }
    public int getManaAdder()
    {
        return manaAdder;
    }
    public void resetManaAdder()
    {
        manaAdder = 0;
    }
    public double getManaMultiplier()
    {
        return manaMultiplier;
    }
    public void resetManaMultiplier()
    {
        manaMultiplier = 0;
    }
}
