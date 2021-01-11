package com.fi0x.deepmagic.items.spells.effects.defensive;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SpEfRegeneration implements ISpellEffect
{
    public static final String NAME = "effect_regeneration";
    private int power = 1;
    private double seconds = 5;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += power + "_attr_";
        ret += seconds;
        return ret;
    }
    @Override
    public void setAttributesFromString(String[] attributes)
    {
        power = Integer.parseInt(attributes[0]);
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
        targetEntity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, (int) (20 * seconds), power - 1, false, true));
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
    public void setHealPower(int value)
    {
        power = value;
    }
    @Override
    public int getHealPower()
    {
        return power;
    }
}
