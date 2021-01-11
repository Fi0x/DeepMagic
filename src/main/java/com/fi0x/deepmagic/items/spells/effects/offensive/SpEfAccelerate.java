package com.fi0x.deepmagic.items.spells.effects.offensive;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/*
 * Accelerates the target upwards
 */
public class SpEfAccelerate implements ISpellEffect
{
    public static final String NAME = "effect_accelerate";
    private int speed = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += speed;
        return ret;
    }
    @Override
    public void setAttributesFromString(String[] attributes)
    {
        speed = Integer.parseInt(attributes[0]);
    }
    @Override
    public ISpellEffect getEffect()
    {
        return this;
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
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
