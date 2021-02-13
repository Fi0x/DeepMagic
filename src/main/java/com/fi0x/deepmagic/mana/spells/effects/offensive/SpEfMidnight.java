package com.fi0x.deepmagic.mana.spells.effects.offensive;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

/*
 * Time gets set to midnight at new-moon
 * Target gets attacked by "shadows"
 */
public class SpEfMidnight implements ISpellEffect
{
    public static final String NAME = "effect_midnight";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Effect: Midnight";
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
        return new double[]{100, 0, 1, 0};
    }

    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
        long current = world.getWorldTime();
        world.setWorldTime(current + 18000 + ((current % 24000 < 18000) ? -(current % 24000) : current % 24000));
        //TODO: Spawn "shadows"
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
    }
}
