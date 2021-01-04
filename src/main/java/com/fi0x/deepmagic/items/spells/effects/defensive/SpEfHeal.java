package com.fi0x.deepmagic.items.spells.effects.defensive;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpEfHeal implements ISpellEffect
{
    public static final String NAME = "effect_heal";
    private int healPower = 1;

    @Override
    public ISpellEffect getEffect()
    {
        return this;
    }
    @Override
    public void applyEffect(EntityLivingBase caster, BlockPos targetPos, World world)
    {
    }
    @Override
    public void applyEffect(EntityLivingBase caster, EntityLivingBase targetEntity)
    {
        targetEntity.heal(healPower);
    }

    @Override
    public void setHealPower(int value)
    {
        this.healPower = value;
    }
    @Override
    public int getHealPower()
    {
        return healPower;
    }
}
