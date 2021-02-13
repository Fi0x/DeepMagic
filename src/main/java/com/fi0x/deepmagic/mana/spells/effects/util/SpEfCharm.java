package com.fi0x.deepmagic.mana.spells.effects.util;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

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
    public String getDisplayName()
    {
        return "Effect: Charm";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME;
        return ret;
    }
    @Override
    public ArrayList<ItemStack> getRequiredItems()
    {
        ArrayList<ItemStack> list = new ArrayList<>();

        list.add(new ItemStack(ModItems.MAGIC_CONVERTER));
        list.add(new ItemStack(Items.WHEAT));
        list.add(new ItemStack(Items.CARROT));
        list.add(new ItemStack(Items.WHEAT_SEEDS));

        return list;
    }

    @Override
    public double[] getCastModifiers()
    {
        return new double[]{40, 0, 0, 0};
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
