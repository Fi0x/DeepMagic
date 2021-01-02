package com.fi0x.deepmagic.items.spells.effects.util;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/*
 * Accelerates the target upwards
 */
public class SpEfAccelerate implements ISpellEffect
{
    private int speed = 1;

    @Override
    public String getName()
    {
        return "effect_accelerate";
    }
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
        targetEntity.addVelocity(0, speed, 0);
    }

    @Override
    public void setPower(int value)
    {
        speed = value;
    }
    @Override
    public int getPower()
    {
        return speed;
    }
}
