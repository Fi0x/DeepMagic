package com.fi0x.deepmagic.items.spells.effects.offensive;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/*
 * Time gets set to midnight at new-moon
 * Target gets attacked by "shadows"
 */
public class SpEfMidnight implements ISpellEffect
{
    public static final String NAME = "effect_midnight";

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
        long current = world.getWorldTime();
        world.setWorldTime(current + 18000 + ((current % 24000 < 18000) ? -(current % 24000) : current % 24000));
        //TODO: Spawn "shadows"
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
    }
}
