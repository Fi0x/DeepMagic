package com.fi0x.deepmagic.world.biomes;

import com.fi0x.deepmagic.init.BiomeInit;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;

public class BiomeProviderInsanity extends BiomeProvider
{
    public BiomeProviderInsanity(World world)
    {
        super(world.getWorldInfo());

        allowedBiomes.clear();
        allowedBiomes.add(BiomeInit.INSANITY);
        allowedBiomes.add(BiomeInit.INSANITY_FOREST);

        getBiomesToSpawnIn().clear();
        getBiomesToSpawnIn().addAll(allowedBiomes);
    }
    @Override
    public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original)
    {
        //Fixme: https://forums.minecraftforge.net/topic/64024-1122-biome-and-dimension-tutorials/
        return super.getModdedBiomeGenerators(worldType, seed, original);
    }
}
