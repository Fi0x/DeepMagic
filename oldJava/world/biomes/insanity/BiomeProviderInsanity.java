package com.fi0x.deepmagic.world.biomes.insanity;

import com.fi0x.deepmagic.init.BiomeInit;
import com.fi0x.deepmagic.world.biomes.layer.CustomGenLayerInsanity;
import com.fi0x.deepmagic.world.biomes.layer.CustomGenLayerInsanityBiomes;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

import javax.annotation.Nonnull;

public class BiomeProviderInsanity extends BiomeProvider
{
    public BiomeProviderInsanity(World world)
    {
        super(world.getWorldInfo());

        allowedBiomes.clear();
        allowedBiomes.addAll(BiomeInit.getInsanityBiomes());

        getBiomesToSpawnIn().clear();
        getBiomesToSpawnIn().addAll(allowedBiomes);
    }
    @Nonnull
    @Override
    public GenLayer[] getModdedBiomeGenerators(@Nonnull WorldType worldType, long seed, @Nonnull GenLayer[] original)
    {
        GenLayer biomes = new CustomGenLayerInsanity(seed);

        biomes = new CustomGenLayerInsanityBiomes(1000, biomes);
        biomes = new GenLayerZoom(1000, biomes);
        biomes = new GenLayerZoom(1001, biomes);
        biomes = new GenLayerZoom(1002, biomes);
        biomes = new GenLayerZoom(1003, biomes);
        biomes = new GenLayerZoom(1004, biomes);

        GenLayer biomeIndexLayer = new GenLayerVoronoiZoom(10, biomes);
        biomeIndexLayer.initWorldGenSeed(seed);

        return new GenLayer[]{
                biomes,
                biomeIndexLayer};
    }
}
