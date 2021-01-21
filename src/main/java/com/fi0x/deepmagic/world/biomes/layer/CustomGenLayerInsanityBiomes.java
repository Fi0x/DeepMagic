package com.fi0x.deepmagic.world.biomes.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import javax.annotation.Nonnull;

public class CustomGenLayerInsanityBiomes extends GenLayer
{
    public CustomGenLayerInsanityBiomes(long seed, GenLayer parent)
    {
        super(seed);
        this.parent = parent;
    }

    @Nonnull
    @Override
    public int[] getInts(int x, int z, int width, int depth)
    {
        int[] src = this.parent.getInts(x, z, width, depth);
        int[] dest = IntCache.getIntCache(width * depth);
        for(int dz = 0; dz < depth; dz++)
        {
            for(int dx = 0; dx < width; dx++)
            {
                initChunkSeed(((dx + x) | 3), ((dz + z) | 3));
                dest[dx + dz * width] = src[dx + dz * width];
            }
        }
        return dest;
    }
}
