package com.fi0x.deepmagic.world.biomes;

import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import javax.annotation.Nonnull;
import java.util.Random;

public class BiomeDepth extends Biome
{
    private static final Block TOP_BLOCK = ModBlocks.DEPTH_STONE;
    private static final Block FILLER_BLOCK = ModBlocks.DEPTH_DIRT;
    private static final int SKY_COLOR = MathHelper.hsvToRGB(0.1F, 0F, 0F);
    private static final int FOLIAGE_COLOR = MathHelper.hsvToRGB(0.33F, 0.48F, 0.22F);

    public BiomeDepth()
    {
        //TODO: Adjust values
        super(new BiomeProperties("Depth").setBaseHeight(0.05F).setHeightVariation(0.001F).setTemperature(1F).setWaterColor(7094447));
        topBlock = TOP_BLOCK.getDefaultState();
        fillerBlock = FILLER_BLOCK.getDefaultState();

        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();

        //TODO: Add spawn-lists

        this.flowers.clear();
        //TODO: use custom flowers

        decorator = new DecoratorDepthBiome();
    }

    @Override
    public void genTerrainBlocks(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.generateBiomeTerrain2(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
    public final void generateBiomeTerrain2(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        //TODO: Check if generating works for cave dimension
        int i = worldIn.getSeaLevel();
        IBlockState iblockstate = this.topBlock;
        IBlockState iblockstate1 = this.fillerBlock;
        int j = -1;
        int k = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int l = x & 15;
        int i1 = z & 15;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(int j1 = 255; j1 >= 0; --j1)
        {
            if(j1 <= rand.nextInt(5))
            {
                chunkPrimerIn.setBlockState(i1, j1, l, BEDROCK);
            } else
            {
                IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);

                if(iblockstate2.getMaterial() == Material.AIR)
                {
                    j = -1;
                } else if(iblockstate2.getBlock() == ModBlocks.DEPTH_STONE)
                {
                    if(j == -1)
                    {
                        if(k <= 0)
                        {
                            iblockstate = AIR;
                            iblockstate1 = ModBlocks.DEPTH_STONE.getDefaultState();
                        } else if(j1 >= i - 4 && j1 <= i + 1)
                        {
                            iblockstate = this.topBlock;
                            iblockstate1 = this.fillerBlock;
                        }

                        if(j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR))
                        {
                            if(this.getTemperature(blockpos$mutableblockpos.setPos(x, j1, z)) < 0.15F)
                            {
                                iblockstate = ICE;
                            } else
                            {
                                iblockstate = WATER;
                            }
                        }

                        j = k;

                        if(j1 >= i - 1)
                        {
                            chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
                        } else if(j1 < i - 7 - k)
                        {
                            iblockstate = AIR;
                            iblockstate1 = ModBlocks.DEPTH_STONE.getDefaultState();
                            chunkPrimerIn.setBlockState(i1, j1, l, ModBlocks.DEPTH_DIRT.getDefaultState());
                        } else
                        {
                            chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
                        }
                    } else if(j > 0)
                    {
                        --j;
                        chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
                    }
                }
            }
        }
    }

    @Override
    public int getSkyColorByTemp(float currentTemperature)
    {
        return SKY_COLOR;
    }
    @Override
    public boolean canRain()
    {
        return false;
    }
    @Override
    public int getFoliageColorAtPos(@Nonnull BlockPos pos)
    {
        return FOLIAGE_COLOR;
    }
}