package com.fi0x.deepmagic.mana.spells.effects.offensive;

import com.fi0x.deepmagic.mana.spells.effects.ISpellEffect;
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
    public String getDisplayName()
    {
        return "Effect: Midnight";
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
        long current = world.getWorldTime();
        world.setWorldTime(current + 18000 + ((current % 24000 < 18000) ? -(current % 24000) : current % 24000));
        //TODO: Spawn "shadows"
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
    }
}
