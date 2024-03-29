package com.fi0x.deepmagic.mana.spells.effects.util;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nullable;
import java.util.ArrayList;

/*
 * Switch between day and night
 */
public class SpEfDayNight implements ISpellEffect
{
    public static final String NAME = "effect_daynight";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Effect: Day-Night";
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
        list.add(new ItemStack(Items.CLOCK));
        list.add(new ItemStack(ModItems.EMPTY_SCROLL));

        return list;
    }

    @Override
    public double[] getCastModifiers()
    {
        return new double[]{100, 0, 0, 4};
    }

    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
        WorldInfo info = world.getWorldInfo();
        info.setWorldTotalTime(info.getWorldTotalTime() + 12000);
        //TODO: Fix it, not working right now
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
    }
}
