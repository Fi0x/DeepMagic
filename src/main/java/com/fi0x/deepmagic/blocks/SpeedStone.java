package com.fi0x.deepmagic.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class SpeedStone extends BlockBase
{
    public SpeedStone(String name, Material material)
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
            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.SPEED, 2*20, 1, false, false));
        }
    }
}