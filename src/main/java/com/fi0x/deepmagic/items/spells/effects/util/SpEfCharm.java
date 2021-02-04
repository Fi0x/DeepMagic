package com.fi0x.deepmagic.items.spells.effects.util;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/*
 * Target becomes friendly
 * Animals breed
 */
public class SpEfCharm implements ISpellEffect
{
    public static final String NAME = "effect_charm";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME;
        return ret;
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
        if(targetEntity instanceof EntityLiving)
        {
            if(((EntityLiving) targetEntity).targetTasks.taskEntries.size() > 0)
            {
                ((EntityLiving) targetEntity).setAttackTarget(null);
                targetEntity.setRevengeTarget(null);
                //TODO: Make hostile entity friendly (Ars magicka uses a custom potion effect)
            } else if(targetEntity instanceof EntityAnimal) ((EntityAnimal) targetEntity).setInLove(caster instanceof EntityPlayer ? (EntityPlayer) caster : null);
        }
    }
}
