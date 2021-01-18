package com.fi0x.deepmagic.world.generators.underground;

import net.minecraft.block.BlockBush;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

public class CustomBushGenerator extends WorldGenerator
{
    private final BlockBush block;
    private final int maxHeight;

    public CustomBushGenerator(BlockBush blockIn, int maxHeight)
    {
        this.block = blockIn;
        this.maxHeight = maxHeight;
    }

    @Override
    public boolean generate(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos position)
    {
        for(int i = 0; i < maxHeight; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - 4, rand.nextInt(4) - 2, rand.nextInt(8) - 4);

            if(worldIn.isAirBlock(blockpos) && blockpos.getY() < worldIn.getHeight() - 1 && this.block.canBlockStay(worldIn, blockpos, this.block.getDefaultState()))
            {
                worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
            }
        }

        return true;
    }
}
