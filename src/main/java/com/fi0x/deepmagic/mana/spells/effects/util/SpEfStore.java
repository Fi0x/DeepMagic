package com.fi0x.deepmagic.mana.spells.effects.util;

import com.fi0x.deepmagic.mana.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/*
 * Stores target in spell until next use
 * Can also open a player specific inventory like ender chests
 */
public class SpEfStore implements ISpellEffect
{
    public static final String NAME = "effect_store";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Effect: Store";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME;
        return ret;
    }
    //TODO: Use only ONE method to apply an effect
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
    }
}
