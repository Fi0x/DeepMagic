package com.fi0x.deepmagic.world.generators.underground;

import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

public class CustomGlowstoneGenerator extends WorldGenerator
{
    @Override
    public boolean generate(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos position)
    {
        for(int i = 0; i < 255; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(16) - 8, rand.nextInt(8) - 4, rand.nextInt(16) - 8);
            Block block = worldIn.getBlockState(blockpos).getBlock();

            if((block == ModBlocks.DEPTH_STONE || block == ModBlocks.DEPTH_DIRT || worldIn.isAirBlock(blockpos)) && blockpos.getY() < worldIn.getHeight() - 1)
            {
                worldIn.setBlockState(blockpos, ModBlocks.DEPTH_GLOWSTONE.getDefaultState(), 2);
                break;
            }
        }
        return true;
    }
}
