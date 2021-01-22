package com.fi0x.deepmagic.world.generators.plants;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

public class CustomGrassGenerator extends WorldGenerator
{
    private final BlockBush GRASS;
    private final int MAX_HEIGHT;

    public CustomGrassGenerator(BlockBush grassBlock)
    {
        this(grassBlock, 128);
    }
    public CustomGrassGenerator(BlockBush grassBlock, int maxHeight)
    {
        GRASS = grassBlock;
        MAX_HEIGHT = maxHeight;
    }

    @Override
    public boolean generate(World worldIn, @Nonnull Random rand, @Nonnull BlockPos position)
    {
        for(IBlockState iblockstate = worldIn.getBlockState(position); (iblockstate.getBlock().isAir(iblockstate, worldIn, position) || iblockstate.getBlock().isLeaves(iblockstate, worldIn, position)) && position.getY() > 0; iblockstate = worldIn.getBlockState(position))
        {
            position = position.down();
        }

        for(int i = 0; i < MAX_HEIGHT; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(16) - 8, rand.nextInt(8) - 4, rand.nextInt(16) - 8);

            if(worldIn.isAirBlock(blockpos) && GRASS.canBlockStay(worldIn, blockpos, GRASS.getDefaultState()))
            {
                worldIn.setBlockState(blockpos, GRASS.getDefaultState(), 2);
            }
        }
        return true;
    }
}
