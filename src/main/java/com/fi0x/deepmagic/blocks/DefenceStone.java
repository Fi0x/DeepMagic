package com.fi0x.deepmagic.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class DefenceStone extends BlockBase
{
    public DefenceStone(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(3.0F);
        setHarvestLevel("pickaxe", 1);
    }

    @Override
    public void onEntityWalk(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Entity entity)
    {
        if(!(entity instanceof EntityPlayer) && !(entity instanceof EntityAnimal))
        {
            entity.motionX = -entity.motionX*5;
            entity.motionZ = -entity.motionZ*5;
            entity.motionY = 0.4;

            if(entity.motionX > 0.4) entity.motionX = 0.4;
            if(entity.motionX < -0.4) entity.motionX = -0.4;
            if(entity.motionZ > 0.4) entity.motionZ = 0.4;
            if(entity.motionZ < -0.4) entity.motionZ = -0.4;
        }
    }
}