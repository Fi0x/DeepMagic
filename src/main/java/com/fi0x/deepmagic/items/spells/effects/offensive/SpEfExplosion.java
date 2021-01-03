package com.fi0x.deepmagic.items.spells.effects.offensive;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpEfExplosion implements ISpellEffect
{
    private int power = 1;
    private boolean environmentalDamage = false;

    @Override
    public String getName()
    {
        return "effect_explosion";
    }
    @Override
    public ISpellEffect getEffect()
    {
        return this;
    }
    @Override
    public void applyEffect(EntityLivingBase caster, BlockPos targetPos, World world)
    {
        world.newExplosion(caster, targetPos.getX(), targetPos.getY(), targetPos.getZ(), power, false, true);
    }
    @Override
    public void applyEffect(EntityLivingBase caster, EntityLivingBase targetEntity)
    {
        targetEntity.world.newExplosion(caster, targetEntity.posX, targetEntity.posY, targetEntity.posZ, power, false, environmentalDamage);
    }

    @Override
    public void setEnvironmentalDmg(boolean state)
    {
        environmentalDamage = state;
    }
    @Override
    public boolean doesEnvironmentalDmg()
    {
        return environmentalDamage;
    }
    @Override
    public void setPower(int value)
    {
        this.power = value;
    }
    @Override
    public int getPower()
    {
        return power;
    }
}
