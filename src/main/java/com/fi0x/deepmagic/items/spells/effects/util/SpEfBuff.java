package com.fi0x.deepmagic.items.spells.effects.util;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;

/*
 * Increases many stats of the target
 */
public class SpEfBuff implements ISpellEffect
{
    @Override
    public String getName()
    {
        return "effect_buff";
    }
    @Override
    public ISpellEffect getEffect()
    {
        return this;
    }
    //TODO: Use one method to apply an effect
    @Override
    public void applyEffect(BlockPos targetPos)
    {
    }
    @Override
    public void applyEffect(EntityLivingBase targetEntity)
    {
    }
}
