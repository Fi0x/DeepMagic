package com.fi0x.deepmagic.items.spells.effects.defensive;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SpEfMiningSpeed implements ISpellEffect
{
    public static final String NAME = "effect_miningspeed";
    private int level = 1;
    private double seconds = 10;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += level + "_attr_";
        ret += seconds;
        return ret;
    }
    @Override
    public void setAttributesFromString(String[] attributes)
    {
        level = Integer.parseInt(attributes[0]);
        seconds = Double.parseDouble(attributes[1]);
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
        targetEntity.addPotionEffect(new PotionEffect(MobEffects.SPEED, (int) (20 * seconds), level - 1, false, true));
    }

    @Override
    public void setDuration(double value)
    {
        seconds = value;
    }
    @Override
    public double getDuration()
    {
        return seconds;
    }
    @Override
    public void setPower(int value)
    {
        level = value;
    }
    @Override
    public int getPower()
    {
        return level;
    }
}
