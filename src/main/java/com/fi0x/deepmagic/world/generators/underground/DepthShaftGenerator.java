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
    protected final IBlockState AIR = Blocks.AIR.getDefaultState();
    private final IBlockState LOG;
    private final IBlockState DECORATION;

    public DepthShaftGenerator(IBlockState log, IBlockState decoration)
    {
        range = 6;
        LOG = log;
        DECORATION = decoration;
    }

    @Override
    public void generate(@Nonnull World worldIn, int x, int z, @Nonnull ChunkPrimer primer)
    {
        if(rand.nextInt(40) > 0) return;
        this.world = worldIn;
        this.rand.setSeed(worldIn.getSeed());

        int currentRadius = rand.nextInt(range) + 2;
        int centerX = rand.nextInt(16 - 2 * currentRadius) + currentRadius;
        int centerZ = rand.nextInt(16 - 2 * currentRadius) + currentRadius;

        digShaft(primer, centerX, centerZ, currentRadius);
    }

    protected void digShaft(@Nonnull ChunkPrimer chunkPrimerIn, int centerX, int centerZ, int currentRadius)
    {
        double radiusAir = currentRadius + 0.4032;
        double radiusWoodMin = currentRadius + 0.4032;
        double radiusWoodMax = currentRadius + 1.0711;
        switch(currentRadius)
        {
            case 4:
                radiusAir = currentRadius + 0.1232;
                radiusWoodMax = currentRadius + 0.4722;
                break;
            case 5:
                radiusAir = currentRadius + 0.0991;
                radiusWoodMin = currentRadius;
                radiusWoodMax = currentRadius + 0.6569;
                break;
            case 7:
                radiusWoodMax = currentRadius + 0.8103;
                break;
        }
        for(int blockX = centerX - currentRadius; blockX <= centerX + currentRadius; ++blockX)
        {
            for(int blockZ = centerZ - currentRadius; blockZ <= centerZ + currentRadius; ++blockZ)
            {
                if(isInside(blockX, centerX, blockZ, centerZ, radiusAir))
                {
                    for(int blockY = 1; blockY < 255; blockY++)
                    {
                        digBlock(chunkPrimerIn, blockX, blockY, blockZ);
                    }
                } else if(isOutside(blockX, centerX, blockZ, centerZ, radiusWoodMin) && isInside(blockX, centerX, blockZ, centerZ, radiusWoodMax))
                {
                    for(int blockY = 1; blockY < 255; blockY++)
                    {
                        placeLog(chunkPrimerIn, blockX, blockY, blockZ);
                    }
                }
            }
        }
    }
    protected void digBlock(ChunkPrimer primer, int x, int y, int z)
    {
        IBlockState state = primer.getBlockState(x, y, z);

        if(isReplaceable(state))
        {
            if(y > 10 && y < 245) primer.setBlockState(x, y, z, AIR);
            else primer.setBlockState(x, y, z, Blocks.OBSIDIAN.getDefaultState());
        }
    }
    protected void placeLog(ChunkPrimer primer, int x, int y, int z)
    {
        IBlockState state = primer.getBlockState(x, y, z);

        if(isReplaceable(state))
        {
            if(y % 10 == 0 && rand.nextInt(4) == 0) primer.setBlockState(x, y, z, DECORATION);
            else primer.setBlockState(x, y, z, LOG);
        }
    }

    private boolean isInside(int x1, int x2, int z1, int z2, double rad)
    {
        int xDiff = x1 - x2;
        int zDiff = z1 - z2;

        int dist = xDiff * xDiff + zDiff * zDiff;
        return dist < rad * rad;
    }
    private boolean isOutside(int x1, int x2, int z1, int z2, double rad)
    {
        int xDiff = x1 - x2;
        int zDiff = z1 - z2;

        int dist = xDiff * xDiff + zDiff * zDiff;
        return dist > rad * rad;
    }
    private boolean isReplaceable(IBlockState state)
    {
        if(state.getBlock() == Blocks.STONE || state.getBlock() == ModBlocks.DEPTH_STONE || state.getBlock() == ModBlocks.INSANITY_STONE) return true;
        if(state.getBlock() == Blocks.DIRT || state.getBlock() == ModBlocks.DEPTH_DIRT || state.getBlock() == ModBlocks.INSANITY_DIRT) return true;
        return false;
    }
}
