package com.fi0x.deepmagic.mana.spells.effects.util;

import com.fi0x.deepmagic.mana.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SpEfGrowth implements ISpellEffect
{
    public static final String NAME = "effect_growth";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Effect: Growth";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME;
        return ret;
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
        //TODO: Use method to apply bone-meal effect on target block
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
    }
}
