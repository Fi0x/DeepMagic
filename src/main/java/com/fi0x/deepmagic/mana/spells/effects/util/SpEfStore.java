package com.fi0x.deepmagic.mana.spells.effects.util;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

/*
 * Stores target in spell until next use
 * Can also open a player specific inventory like ender chests
 */
public class SpEfStore implements ISpellEffect
{
    public static final String NAME = "effect_store";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Effect: Store";
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
        list.add(new ItemStack(Blocks.CHEST));

        return list;
    }

    @Override
    public double[] getCastModifiers()
    {
        return new double[]{20, 0, 0, 0};
    }

    //TODO: Use only ONE method to apply an effect
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
    }
}
