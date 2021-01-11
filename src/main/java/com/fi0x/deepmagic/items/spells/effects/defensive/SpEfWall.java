package com.fi0x.deepmagic.items.spells.effects.defensive;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/*
 * Creates a wall of blocks
 * Wall will be destroyed after a certain amount of time
 */
public class SpEfWall implements ISpellEffect
{
    public static final String NAME = "effect_wall";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME;
        return ret;
    }
    @Override
    public ISpellEffect getEffect()
    {
        return this;
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
        //TODO: Use method to create a wall
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
    }
}
