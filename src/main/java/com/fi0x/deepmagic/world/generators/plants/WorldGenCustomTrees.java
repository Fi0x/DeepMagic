package com.fi0x.deepmagic.world.generators.plants;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.world.biomes.insanity.BiomeInsanityForestLarge;
import com.fi0x.deepmagic.world.biomes.insanity.BiomeInsanityForestMixed;
import com.fi0x.deepmagic.world.biomes.insanity.BiomeInsanityForestSmall;
import com.fi0x.deepmagic.world.biomes.insanity.BiomeInsanityPlains;
import net.minecraft.block.BlockLeaves;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

public class WorldGenCustomTrees implements IWorldGenerator
{
    private final WorldGenerator INSANITY_SMALL = new WorldGenTrees(true, 4, ModBlocks.INSANITY_LOG.getDefaultState(), ModBlocks.INSANITY_LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE), false);
    private final WorldGenerator INSANITY_NORMAL = new TreeGenInsanityMedium(true);
    private final WorldGenerator INSANITY_LARGE = new TreeGenInsanityLarge(true);

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.provider.getDimension() == ConfigHandler.dimensionIdInsanityID)
        {
            runGenerator(INSANITY_SMALL, world, random, chunkX, chunkZ, 16, 7, BiomeInsanityForestSmall.class);
            runGenerator(INSANITY_SMALL, world, random, chunkX, chunkZ, 16, 4, BiomeInsanityForestMixed.class);

            runGenerator(INSANITY_NORMAL, world, random, chunkX, chunkZ, 8, 0.3, BiomeInsanityForestSmall.class);
            runGenerator(INSANITY_NORMAL, world, random, chunkX, chunkZ, 8, 2, BiomeInsanityForestMixed.class);
            runGenerator(INSANITY_NORMAL, world, random, chunkX, chunkZ, 8, 1, BiomeInsanityForestLarge.class);

            runGenerator(INSANITY_LARGE, world, random, chunkX, chunkZ, 2, 0.3, BiomeInsanityForestMixed.class);
            runGenerator(INSANITY_LARGE, world, random, chunkX, chunkZ, 2, 1, BiomeInsanityForestLarge.class);
            runGenerator(INSANITY_LARGE, world, random, chunkX, chunkZ, 2, 0.002, BiomeInsanityPlains.class);
        }
    }

    private void runGenerator(WorldGenerator generator, World world, Random rand, int chunkX, int chunkZ, int posVariation, double chance, Class<?> spawnBiome)
    {
        if(chance < 1)
        {
            if(rand.nextDouble() < chance) chance = 1;
            else chance = 0;
        }
        int heightDiff = 2;
        for(int i = 0; i < chance; i++)
        {
            BlockPos pos = new BlockPos(chunkX * 16 + rand.nextInt(posVariation) + 16 - posVariation / 2, -1 + rand.nextInt(heightDiff), chunkZ * 16 + rand.nextInt(posVariation) + 16 - posVariation / 2);
            pos = world.getHeight(pos);
            Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
            if(biome == spawnBiome) generator.generate(world, rand, pos);
        }
    }

    public static void register()
    {
        GameRegistry.registerWorldGenerator(new WorldGenCustomTrees(), 0);
    }
}