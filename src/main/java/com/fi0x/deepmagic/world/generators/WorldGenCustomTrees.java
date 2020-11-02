package com.fi0x.deepmagic.world.generators;

import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.world.biome.BiomeInsanity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

public class WorldGenCustomTrees implements IWorldGenerator
{
    private final WorldGenerator INSANITY = new ModTreeGenerator(true);

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if (world.provider.getDimension() == ConfigHandler.dimensionIdInsanityID)
        {
            runGenerator(INSANITY, world, random, chunkX, chunkZ, 1, BiomeInsanity.class);
        }
    }

    private void runGenerator(WorldGenerator generator, World world, Random rand, int chunkX, int chunkZ, double chance, Class<?> spawnBiome)
    {
        if(chance < 1)
        {
            if(rand.nextDouble() < chance) chance = 1;
            else chance = 0;
        }
        int heightDiff = 2;
        for(int i = 0; i < chance; i++)
        {
            BlockPos pos = new BlockPos(chunkX * 16 + 10 + rand.nextInt(15), -1 + rand.nextInt(heightDiff), chunkZ * 16 + 10 + rand.nextInt(15));
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