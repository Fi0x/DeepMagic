package com.fi0x.deepmagic.mana.spells.effects.offensive;

import com.fi0x.deepmagic.mana.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SpEfExplosion implements ISpellEffect
{
    public static final String NAME = "effect_explosion";
    private int power = 1;
    private boolean environmentalDamage = false;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Effect: Explosion";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += power + "_attr_";
        ret += environmentalDamage;
        return ret;
    }
    @Override
    public void setAttributesFromString(ArrayList<String> attributes)
    {
        power = Integer.parseInt(attributes.get(0));
        environmentalDamage = Boolean.parseBoolean(attributes.get(1));
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
        world.newExplosion(caster, targetPos.getX(), targetPos.getY(), targetPos.getZ(), power, false, environmentalDamage);
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
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
