package com.fi0x.deepmagic.world.biomes.depth;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.world.generators.underground.CustomBushGenerator;
import net.minecraft.init.Blocks;
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

public class DecoratorDepthBiome extends BiomeDecorator
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
    public WorldGenerator depthFlowerGen = new CustomBushGenerator(ModBlocks.DEPTH_FLOWER, 250);
    public final int flowersPerChunk = 50;

    private final int MAX_ORE_HEIGHT = 250;
    private final int MIN_ORE_HEIGHT = 0;

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
            this.dirtGen = new WorldGenMinable(ModBlocks.DEPTH_DIRT.getDefaultState(), this.chunkProviderSettings.dirtSize);
            this.gravelOreGen = new WorldGenMinable(Blocks.OBSIDIAN.getDefaultState(), this.chunkProviderSettings.gravelSize);
            this.coalGen = new WorldGenMinable(ModBlocks.DEPTH_COAL_ORE.getDefaultState(), this.chunkProviderSettings.coalSize);
            this.ironGen = new WorldGenMinable(ModBlocks.DEPTH_IRON_ORE.getDefaultState(), this.chunkProviderSettings.ironSize);
            this.goldGen = new WorldGenMinable(ModBlocks.DEPTH_GOLD_ORE.getDefaultState(), this.chunkProviderSettings.goldSize);
            this.redstoneGen = new WorldGenMinable(ModBlocks.DEPTH_REDSTONE_ORE.getDefaultState(), this.chunkProviderSettings.redstoneSize);
            this.diamondGen = new WorldGenMinable(ModBlocks.DEPTH_DIAMOND_ORE.getDefaultState(), this.chunkProviderSettings.diamondSize);
            this.lapisGen = new WorldGenMinable(ModBlocks.DEPTH_LAPIS_ORE.getDefaultState(), this.chunkProviderSettings.lapisSize);
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

        if(TerrainGen.decorate(worldIn, random, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.SHROOM))
        {
            for(int l3 = 0; l3 < flowersPerChunk; ++l3)
            {
                if(random.nextInt(2) == 0)
                {
                    int i8 = 8;
                    int l11 = 8;
                    BlockPos blockpos2 = worldIn.getHeight(this.chunkPos.add(i8, 0, l11));
                    depthFlowerGen.generate(worldIn, random, blockpos2);
                }
            }

            if(random.nextInt(2) == 0)
            {
                int i4 = 8;
                int k8 = 8;
                int j12 = worldIn.getHeight(chunkPos.add(i4, 0, k8)).getY() * 2;

                if(j12 > 0)
                {
                    int k15 = random.nextInt(j12);
                    depthFlowerGen.generate(worldIn, random, chunkPos.add(i4, k15, k8));
                }
            }
        }

        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Post(worldIn, random, forgeChunkPos));
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
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.coalCount, this.coalGen, MIN_ORE_HEIGHT, MAX_ORE_HEIGHT);
        if(TerrainGen.generateOre(worldIn, random, ironGen, chunkPos, OreGenEvent.GenerateMinable.EventType.IRON))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.ironCount, this.ironGen, MIN_ORE_HEIGHT, MAX_ORE_HEIGHT);
        if(TerrainGen.generateOre(worldIn, random, goldGen, chunkPos, OreGenEvent.GenerateMinable.EventType.GOLD))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.goldCount, this.goldGen, MIN_ORE_HEIGHT, MAX_ORE_HEIGHT);
        if(TerrainGen.generateOre(worldIn, random, redstoneGen, chunkPos, OreGenEvent.GenerateMinable.EventType.REDSTONE))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.redstoneCount, this.redstoneGen, MIN_ORE_HEIGHT, MAX_ORE_HEIGHT);
        if(TerrainGen.generateOre(worldIn, random, diamondGen, chunkPos, OreGenEvent.GenerateMinable.EventType.DIAMOND))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.diamondCount, this.diamondGen, MIN_ORE_HEIGHT, MAX_ORE_HEIGHT);
        if(TerrainGen.generateOre(worldIn, random, lapisGen, chunkPos, OreGenEvent.GenerateMinable.EventType.LAPIS))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.lapisCount, this.lapisGen, MIN_ORE_HEIGHT, MAX_ORE_HEIGHT);
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
}