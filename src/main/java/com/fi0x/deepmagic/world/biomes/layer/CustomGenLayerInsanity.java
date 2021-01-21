package com.fi0x.deepmagic.world.biomes.layer;

import com.fi0x.deepmagic.init.BiomeInit;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import javax.annotation.Nonnull;

public class CustomGenLayerInsanity extends GenLayer
{
    private final Biome[] biomes = BiomeInit.getInsanityBiomes().toArray(new Biome[0]);

    public CustomGenLayerInsanity(long seed, GenLayer parent)
    {
        super(seed);
        this.parent = parent;
    }
    public CustomGenLayerInsanity(long seed)
    {
        super(seed);
    }

    @Nonnull
    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] dest = IntCache.getIntCache(areaWidth * areaHeight);
        for(int i = 0; i < areaHeight; i++)
        {
            for(int j = 0; j < areaWidth; j++)
            {
                initChunkSeed(j + areaX, i + areaY);
                dest[j + i * areaHeight] = Biome.getIdForBiome(biomes[nextInt(biomes.length)]);
            }
        }

        return dest;
    }
}
