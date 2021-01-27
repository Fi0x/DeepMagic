package com.fi0x.deepmagic.world.generators.underground;

import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

import javax.annotation.Nonnull;

public class DepthShaftGenerator extends MapGenBase
{
    protected static final IBlockState AIR = Blocks.AIR.getDefaultState();

    public DepthShaftGenerator()
    {
        range = 5;
    }

    @Override
    public void generate(@Nonnull World worldIn, int x, int z, @Nonnull ChunkPrimer primer)
    {
        if(rand.nextInt(25) > 0) return;
        this.world = worldIn;
        this.rand.setSeed(worldIn.getSeed());

        int currentRadius = rand.nextInt(range) + 2;
        int centerX = rand.nextInt(16 - 2 * currentRadius) + currentRadius;
        int centerZ = rand.nextInt(16 - 2 * currentRadius) + currentRadius;

        digShaft(primer, centerX, centerZ, currentRadius);
    }

    protected void digShaft(@Nonnull ChunkPrimer chunkPrimerIn, int centerX, int centerZ, int currentRadius)
    {
        for(int bx = centerX - currentRadius; bx <= centerX + currentRadius; ++bx)
        {
            for(int bz = centerZ - currentRadius; bz <= centerZ + currentRadius; ++bz)
            {
                if(distSq(bx, centerX, bz, centerZ) > (currentRadius + 0.5) * (currentRadius + 0.5)) continue;
                for(int y = 1; y < 255; y++)
                {
                    this.digBlock(chunkPrimerIn, bx, y, bz);
                }
            }
        }
    }
    protected void digBlock(ChunkPrimer data, int x, int y, int z)
    {
        IBlockState state = data.getBlockState(x, y, z);

        if(state.getBlock() == Blocks.STONE || state.getBlock() == ModBlocks.DEPTH_STONE || state.getBlock() == ModBlocks.DEPTH_DIRT || state.getBlock() == ModBlocks.INSANITY_STONE || state.getBlock() == ModBlocks.INSANITY_DIRT)
        {
            data.setBlockState(x, y, z, AIR);
        }
    }

    private int distSq(int x1, int x2, int z1, int z2)
    {
        int xDiff = x1 - x2;
        int zDiff = z1 - z2;

        return xDiff * xDiff + zDiff * zDiff;
    }
}
