package com.fi0x.deepmagic.world.biomes.layer;

import com.fi0x.deepmagic.init.BiomeInit;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import javax.annotation.Nonnull;

public class CustomGenLayer extends GenLayer
{
    private Biome[] biomes = new Biome[]{
            BiomeInit.INSANITY,
            BiomeInit.INSANITY_FOREST};


    public CustomGenLayer(long seed, GenLayer parent)
    {
        super(seed);
        this.parent = parent;
    }
    public CustomGenLayer(long seed)
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
