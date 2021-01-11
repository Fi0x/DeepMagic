package com.fi0x.deepmagic.items.spells.effects.util;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SpEfDig implements ISpellEffect
{
    public static final String NAME = "effect_dig";
    private boolean autoSmelt = false;
    private int fortune = 0;
    private boolean silkTouch = false;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += autoSmelt + "_attr_";
        ret += fortune + "_attr_";
        ret += silkTouch;
        return ret;
    }
    @Override
    public void setAttributesFromString(String[] attributes)
    {
        autoSmelt = Boolean.parseBoolean(attributes[0]);
        fortune = Integer.parseInt(attributes[1]);
        silkTouch = Boolean.parseBoolean(attributes[2]);
    }
    @Override
    public ISpellEffect getEffect()
    {
        return this;
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
        //TODO: Use method to dig the target block
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
    }

    @Override
    public void setAutoSmelt(boolean state)
    {
        autoSmelt = state;
    }
    @Override
    public boolean hasAutoSmelt()
    {
        return autoSmelt;
    }
    @Override
    public void setFortune(int level)
    {
        fortune = level;
    }
    @Override
    public int getFortuneLvl()
    {
        return fortune;
    }
    @Override
    public void setSilkTouch(boolean state)
    {
        silkTouch = state;
    }
    @Override
    public boolean hasSilkTouch()
    {
        return silkTouch;
    }
}
