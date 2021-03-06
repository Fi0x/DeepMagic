package com.fi0x.deepmagic.world.biomes.insanity;

import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nonnull;
import java.util.Random;

public class DecoratorInsanityBiome extends BiomeDecorator
{
    public boolean decorating;
    public BlockPos chunkPos;
    public ChunkGeneratorSettings chunkProviderSettings;
    public WorldGenerator dirtGen;
    public WorldGenerator gravelOreGen;
    public WorldGenerator coalGen;
    public WorldGenerator ironGen;
    public WorldGenerator goldGen;
    public WorldGenerator redstoneGen;
    public WorldGenerator diamondGen;
    public WorldGenerator lapisGen;
    private int flowersPerChunk = 2;
    private int flowerHeight = 64;
    private int grassPerChunk = 1;

    public void setOptions(int flowersPerChunk, int flowerHeight, int grassPerChunk)
    {
        this.flowersPerChunk = flowersPerChunk;
        this.flowerHeight = flowerHeight;
        this.grassPerChunk = grassPerChunk;
    }

    @Override
    public void decorate(@Nonnull World worldIn, @Nonnull Random random, @Nonnull Biome biome, @Nonnull BlockPos pos)
    {
        if(this.decorating)
        {
            throw new RuntimeException("Already decorating");
        } else
        {
            this.chunkProviderSettings = ChunkGeneratorSettings.Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
            this.chunkPos = pos;
            this.dirtGen = new WorldGenMinable(ModBlocks.INSANITY_DIRT.getDefaultState(), this.chunkProviderSettings.dirtSize);
            this.gravelOreGen = new WorldGenMinable(ModBlocks.INSANITY_DIRT.getDefaultState(), this.chunkProviderSettings.gravelSize);
            this.coalGen = new WorldGenMinable(ModBlocks.INSANITY_COAL_ORE.getDefaultState(), this.chunkProviderSettings.coalSize);
            this.ironGen = new WorldGenMinable(ModBlocks.INSANITY_IRON_ORE.getDefaultState(), this.chunkProviderSettings.ironSize);
            this.goldGen = new WorldGenMinable(ModBlocks.INSANITY_GOLD_ORE.getDefaultState(), this.chunkProviderSettings.goldSize);
            this.redstoneGen = new WorldGenMinable(ModBlocks.INSANITY_REDSTONE_ORE.getDefaultState(), this.chunkProviderSettings.redstoneSize);
            this.diamondGen = new WorldGenMinable(ModBlocks.INSANITY_DIAMOND_ORE.getDefaultState(), this.chunkProviderSettings.diamondSize);
            this.lapisGen = new WorldGenMinable(ModBlocks.INSANITY_LAPIS_ORE.getDefaultState(), this.chunkProviderSettings.lapisSize);
            this.genDecorations(biome, worldIn, random);
            this.decorating = false;
        }
    }

    @Override
    protected void genDecorations(@Nonnull Biome biomeIn, @Nonnull World worldIn, @Nonnull Random random)
    {
        net.minecraft.util.math.ChunkPos forgeChunkPos = new net.minecraft.util.math.ChunkPos(chunkPos);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(worldIn, random, forgeChunkPos));
        this.generateOres(worldIn, random);

        if(TerrainGen.decorate(worldIn, random, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.FLOWERS))
        {
            for(int flowerCounter = 0; flowerCounter < this.flowersPerChunk; ++flowerCounter)
            {
                int i7 = random.nextInt(16) + 8;
                int l10 = random.nextInt(16) + 8;
                int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

                if(j14 > 0)
                {
                    int k17 = random.nextInt(j14);
                    BlockPos blockpos1 = this.chunkPos.add(i7, k17, l10);

                    for(int i = 0; i < flowerHeight; ++i)
                    {
                        BlockPos blockpos = blockpos1.add(random.nextInt(15) - 7, random.nextInt(7) - 3, random.nextInt(15) - 7);
                        if(worldIn.isAirBlock(blockpos) && blockpos.getY() < 255)
                        {
                            biomeIn.plantFlower(worldIn, random, blockpos);
                        }
                    }
                }
            }
        }

        if(TerrainGen.decorate(worldIn, random, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.GRASS))
        {
            for(int i3 = 0; i3 < this.grassPerChunk; ++i3)
            {
                int j7 = random.nextInt(16) + 8;
                int i11 = random.nextInt(16) + 8;
                int k14 = worldIn.getHeight(this.chunkPos.add(j7, 0, i11)).getY() * 2;

                if(k14 > 0)
                {
                    int l17 = random.nextInt(k14);
                    biomeIn.getRandomWorldGenForGrass(random).generate(worldIn, random, this.chunkPos.add(j7, l17, i11));
                }
            }
        }
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldIn, random, forgeChunkPos));
    }

    @Override
    protected void generateOres(@Nonnull World worldIn, @Nonnull Random random)
    {
        net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(worldIn, random, chunkPos));
        if(TerrainGen.generateOre(worldIn, random, dirtGen, chunkPos, OreGenEvent.GenerateMinable.EventType.DIRT))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.dirtCount, this.dirtGen, this.chunkProviderSettings.dirtMinHeight, this.chunkProviderSettings.dirtMaxHeight);
        if(TerrainGen.generateOre(worldIn, random, gravelOreGen, chunkPos, OreGenEvent.GenerateMinable.EventType.GRAVEL))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.gravelCount, this.gravelOreGen, this.chunkProviderSettings.gravelMinHeight, this.chunkProviderSettings.gravelMaxHeight);
        if(TerrainGen.generateOre(worldIn, random, coalGen, chunkPos, OreGenEvent.GenerateMinable.EventType.COAL))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.coalCount, this.coalGen, this.chunkProviderSettings.coalMinHeight, this.chunkProviderSettings.coalMaxHeight);
        if(TerrainGen.generateOre(worldIn, random, ironGen, chunkPos, OreGenEvent.GenerateMinable.EventType.IRON))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.ironCount, this.ironGen, this.chunkProviderSettings.ironMinHeight, this.chunkProviderSettings.ironMaxHeight);
        if(TerrainGen.generateOre(worldIn, random, goldGen, chunkPos, OreGenEvent.GenerateMinable.EventType.GOLD))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.goldCount, this.goldGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);
        if(TerrainGen.generateOre(worldIn, random, redstoneGen, chunkPos, OreGenEvent.GenerateMinable.EventType.REDSTONE))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.redstoneCount, this.redstoneGen, this.chunkProviderSettings.redstoneMinHeight, this.chunkProviderSettings.redstoneMaxHeight);
        if(TerrainGen.generateOre(worldIn, random, diamondGen, chunkPos, OreGenEvent.GenerateMinable.EventType.DIAMOND))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.diamondCount, this.diamondGen, this.chunkProviderSettings.diamondMinHeight, this.chunkProviderSettings.diamondMaxHeight);
        if(TerrainGen.generateOre(worldIn, random, lapisGen, chunkPos, OreGenEvent.GenerateMinable.EventType.LAPIS))
            this.genStandardOre2(worldIn, random, this.chunkProviderSettings.lapisCount, this.lapisGen, this.chunkProviderSettings.lapisCenterHeight, this.chunkProviderSettings.lapisSpread);
        net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(worldIn, random, chunkPos));
    }

    @Override
    protected void genStandardOre1(@Nonnull World worldIn, @Nonnull Random random, int blockCount, @Nonnull WorldGenerator generator, int minHeight, int maxHeight)
    {
        if(maxHeight < minHeight)
        {
            int i = minHeight;
            minHeight = maxHeight;
            maxHeight = i;
        } else if(maxHeight == minHeight)
        {
            if(minHeight < 255) ++maxHeight;
            else --minHeight;
        }

        for(int j = 0; j < blockCount; ++j)
        {
            BlockPos blockpos = this.chunkPos.add(random.nextInt(16), random.nextInt(maxHeight - minHeight) + minHeight, random.nextInt(16));
            generator.generate(worldIn, random, blockpos);
        }
    }

    @Override
    protected void genStandardOre2(@Nonnull World worldIn, @Nonnull Random random, int blockCount, @Nonnull WorldGenerator generator, int centerHeight, int spread)
    {
        for(int i = 0; i < blockCount; ++i)
        {
            BlockPos blockpos = this.chunkPos.add(random.nextInt(16), random.nextInt(spread) + random.nextInt(spread) + centerHeight - spread, random.nextInt(16));
            generator.generate(worldIn, random, blockpos);
        }
    }
}