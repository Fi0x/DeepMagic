package com.fi0x.deepmagic.world.biomes.layer;

import com.fi0x.deepmagic.init.BiomeInit;
import net.minecraft.world.biome.Biome;
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

    public CustomGenLayerInsanityBiomes(long seed)
    {
        super(seed);
    }

    @Nonnull
    @Override
    public int[] getInts(int x, int z, int width, int depth)
    {
        /*
         TODO: Check how to optimize for biome amount (currently 4)
           https://github.com/ttocskcaj/ElementalCraft
         */
        int[] src = this.parent.getInts(x, z, width, depth);
        int[] dest = IntCache.getIntCache(width * depth);
        for(int dz = 0; dz < depth; dz++)
        {
            for(int dx = 0; dx < width; dx++)
            {
                initChunkSeed(((dx + x) | 3), ((dz + z) | 3));

                int ox = this.nextInt(3) + 1;
                int oz = this.nextInt(3) + 1;

                if(((dx + x) & 3) == ox && ((dz + z) & 3) == oz)
                {
                    // determine which of the 4
                    if(((dx + x) & 4) == 0)
                    {
                        if(((dz + z) & 4) == 0) dest[dx + dz * width] = getBiomeAt(dx + x, dz + z, 0);
                        else dest[dx + dz * width] = getBiomeAt(dx + x, dz + z, 1);

                    } else
                    {
                        if(((dz + z) & 4) == 0) dest[dx + dz * width] = getBiomeAt(dx + x, dz + z, 2);
                        else dest[dx + dz * width] = getBiomeAt(dx + x, dz + z, 3);

                    }

                } else dest[dx + dz * width] = src[dx + dz * width];

            }
        }


        return dest;
    }

    private int getBiomeAt(int mapX, int mapZ, int index)
    {
        int regionX = (mapX + 4) >> 3;
        int regionZ = (mapZ + 4) >> 3;

        Biome[] biomes = BiomeInit.getInsanityBiomes().toArray(new Biome[0]);

        this.initChunkSeed(regionX, regionZ);
        int offset = this.nextInt(biomes.length);

        Biome returnBiome = biomes[(index + offset) % biomes.length];

        return Biome.getIdForBiome(returnBiome);
    }
}
