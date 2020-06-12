package com.fi0x.deepmagic.blocks.effectstones;

import com.fi0x.deepmagic.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class SaturationStone extends BlockBase
{
    public SaturationStone(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(3.0F);
        setHarvestLevel("pickaxe", 1);
    }

    @Override
    public void onEntityWalk(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Entity entity)
    {
        if(entity instanceof EntityPlayer)
        {
            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.SATURATION, 2*20, 0, false, false));
        }
    }
}