package com.fi0x.deepmagic.world.biomes.depth;

import com.fi0x.deepmagic.init.ModBlocks;
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

    private final int MAX_ORE_HEIGHT = 120;
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
            this.dirtGen = new WorldGenMinable(ModBlocks.DEPTH_DIRT.getDefaultState(), 20);
            this.gravelOreGen = new WorldGenMinable(Blocks.OBSIDIAN.getDefaultState(), 20);
            this.coalGen = new WorldGenMinable(ModBlocks.DEPTH_COAL_ORE.getDefaultState(), 15);
            this.ironGen = new WorldGenMinable(ModBlocks.DEPTH_IRON_ORE.getDefaultState(), 13);
            this.goldGen = new WorldGenMinable(ModBlocks.DEPTH_GOLD_ORE.getDefaultState(), 11);
            this.redstoneGen = new WorldGenMinable(ModBlocks.DEPTH_REDSTONE_ORE.getDefaultState(), 10);
            this.diamondGen = new WorldGenMinable(ModBlocks.DEPTH_DIAMOND_ORE.getDefaultState(), 10);
            this.lapisGen = new WorldGenMinable(ModBlocks.DEPTH_LAPIS_ORE.getDefaultState(), 10);
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
            if(minHeight < 128) ++maxHeight;
            else --minHeight;
        }

        for(int j = 0; j < blockCount; ++j)
        {
            BlockPos blockpos = this.chunkPos.add(random.nextInt(16), random.nextInt(maxHeight - minHeight) + minHeight, random.nextInt(16));
            generator.generate(worldIn, random, blockpos);
        }
    }
}