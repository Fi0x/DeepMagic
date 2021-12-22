package com.fi0x.deepmagic.world.generators.underground;

import com.fi0x.deepmagic.blocks.depth.DepthGlowstone;
import com.fi0x.deepmagic.blocks.depth.DepthLog;
import com.fi0x.deepmagic.init.ModBlocks;
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
        for (int x = 0; x < 16; x++)
        {
            for (int z = 0; z < 16; z++)
            {
                BlockPos blockpos = position.add(x, 0, z);
                if(!(worldIn.getBlockState(blockpos).getBlock() instanceof DepthLog))
                    continue;

                blockpos = new BlockPos(blockpos.getX(), 0, blockpos.getZ());
                for(int y = 10; y <= 120; y += 10)
                {
                    if(worldIn.isAirBlock(blockpos.add(0, y, 0)))
                        worldIn.setBlockState(blockpos.add(0, y, 0), ModBlocks.DEPTH_GLOWSTONE.getDefaultState(), 2);
                }
            }
        }
        return true;
    }
}