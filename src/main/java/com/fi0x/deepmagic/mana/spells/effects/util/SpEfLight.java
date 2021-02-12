package com.fi0x.deepmagic.mana.spells.effects.util;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.mana.spells.effects.ISpellEffect;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/*
 * Create a light source
 */
public class SpEfLight implements ISpellEffect
{
    public static final String NAME = "effect_light";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Effect: Light";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME;
        return ret;
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
        if(world.getBlockState(targetPos) instanceof BlockAir) world.setBlockState(targetPos, ModBlocks.MAGIC_LIGHT.getDefaultState());
        else if(world.getBlockState(targetPos.up()) instanceof BlockAir) world.setBlockState(targetPos, ModBlocks.MAGIC_LIGHT.getDefaultState());
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
    }
}
