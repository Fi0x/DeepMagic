package com.fi0x.deepmagic.mana.spells.effects.util;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SpEfStorm implements ISpellEffect
{
    public static final String NAME = "effect_storm";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Effect: Storm";
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
        list.add(new ItemStack(Items.WATER_BUCKET));
        list.add(new ItemStack(Items.ARROW));

        return list;
    }

    @Override
    public double[] getCastModifiers()
    {
        return new double[]{80, 0, 0, 4};
    }

    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
        world.getWorldInfo().setThundering(true);
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
    }
}
