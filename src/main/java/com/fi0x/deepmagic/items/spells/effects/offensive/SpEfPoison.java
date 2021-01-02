package com.fi0x.deepmagic.items.spells.effects.offensive;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpEfPoison implements ISpellEffect
{
    private int power = 1;

    @Override
    public String getName()
    {
        return "effect_poison";
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
        targetEntity.addPotionEffect(new PotionEffect(MobEffects.POISON, 20 * 5, power - 1, false, true));
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
