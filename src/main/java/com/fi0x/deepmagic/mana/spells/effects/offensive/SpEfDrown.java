package com.fi0x.deepmagic.mana.spells.effects.offensive;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SpEfDrown implements ISpellEffect
{
    public static final String NAME = "effect_drown";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Effect: Drown";
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
        //TODO: Recipe missing

        return list;
    }

    @Override
    public double[] getCastModifiers()
    {
        return new double[]{20, 0, 0, 0};
    }

    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
        //TODO: Use method to apply drown effect
    }
}
