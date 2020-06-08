package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.world.biome.BiomeInsanity;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeInit
{
	public static final Biome INSANITY = new BiomeInsanity();
	
	public static void registerBiomes()
	{
		initBiome(INSANITY, "Insanity", BiomeType.WARM, Type.HOT, Type.SPARSE, Type.DRY, Type.SAVANNA, Type.SPOOKY);
	}
	
	private static Biome initBiome(Biome biome, String name, BiomeType biomeType, Type... types)
	{
		biome.setRegistryName(name);
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, types);
		BiomeManager.addBiome(biomeType, new BiomeEntry(biome, 0));
		BiomeManager.addSpawnBiome(biome);
		return biome;
	}
}