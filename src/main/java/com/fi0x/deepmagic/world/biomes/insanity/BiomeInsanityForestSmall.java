package com.fi0x.deepmagic.world.biomes.insanity;

import com.fi0x.deepmagic.entities.mobs.EntityCockroach;
import com.fi0x.deepmagic.entities.mobs.EntityDepthMage;
import com.fi0x.deepmagic.entities.mobs.EntityHoveringOrb;
import com.fi0x.deepmagic.entities.mobs.EntityInsanityCow;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.world.generators.plants.CustomGrassGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

public class BiomeInsanityForestSmall extends Biome
{
    private static final Block TOP_BLOCK = ModBlocks.INSANITY_GRASS;
    private static final Block FILLER_BLOCK = ModBlocks.INSANITY_DIRT;
    private static final int SKY_COLOR = 8396555;
    private static final int FOLIAGE_COLOR = 14907648;

    public BiomeInsanityForestSmall()
    {
        super(new BiomeProperties("InsanityForestSmall").setBaseHeight(0.1F).setHeightVariation(0.15F).setTemperature(1F).setWaterColor(7094447));
        topBlock = TOP_BLOCK.getDefaultState();
        fillerBlock = FILLER_BLOCK.getDefaultState();

        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();

        if(ConfigHandler.allowCockroach) this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityCockroach.class, 20, 1, 2));
        if(ConfigHandler.allowInsanityCow) this.spawnableCreatureList.add(new SpawnListEntry(EntityInsanityCow.class, 5, 1, 3));
        if(ConfigHandler.allowDepthMage) this.spawnableCreatureList.add(new SpawnListEntry(EntityDepthMage.class, 1, 1, 2));
        if(ConfigHandler.allowHoveringOrb) this.spawnableMonsterList.add(new SpawnListEntry(EntityHoveringOrb.class, 5, 1, 2));

        this.flowers.clear();
        addFlower(ModBlocks.INSANITY_FLOWER.getDefaultState(), 20);
        addFlower(ModBlocks.INSANITY_FLOWER4.getDefaultState(), 20);
        addFlower(ModBlocks.INSANITY_FLOWER5.getDefaultState(), 20);

        decorator = new DecoratorInsanityBiome();
    }

    @Override
    public void genTerrainBlocks(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.generateBiomeTerrain2(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
    public final void generateBiomeTerrain2(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
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
                } else if(iblockstate2.getBlock() == ModBlocks.INSANITY_STONE)
                {
                    if(j == -1)
                    {
                        if(k <= 0)
                        {
                            iblockstate = AIR;
                            iblockstate1 = ModBlocks.INSANITY_STONE.getDefaultState();
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
                            iblockstate1 = ModBlocks.INSANITY_STONE.getDefaultState();
                            chunkPrimerIn.setBlockState(i1, j1, l, GRAVEL);
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

    @Nonnull
    @Override
    public WorldGenerator getRandomWorldGenForGrass(@Nonnull Random rand)
    {
        return new CustomGrassGenerator(ModBlocks.INSANITY_TALL_GRASS);
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