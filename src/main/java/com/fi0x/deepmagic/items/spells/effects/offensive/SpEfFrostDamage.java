package com.fi0x.deepmagic.items.spells.effects.offensive;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;

public class SpEfFrostDamage implements ISpellEffect
{
    @Override
    public String getName()
    {
        return "effect_frostdamage";
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
