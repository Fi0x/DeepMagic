package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.world.biomes.BiomeDepth;
import com.fi0x.deepmagic.world.biomes.BiomeInsanity;
import com.fi0x.deepmagic.world.biomes.BiomeInsanityForest;
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
	public static final Biome INSANITY_FOREST = new BiomeInsanityForest();

	public static final Biome DEPTH = new BiomeDepth();

	public static void registerBiomes()
	{
		initBiome(INSANITY, "Insanity", BiomeType.WARM, Type.HOT, Type.SPARSE, Type.DRY, Type.SAVANNA, Type.SPOOKY);
		initBiome(INSANITY_FOREST, "InsanityForest", BiomeType.WARM, Type.DRY);

		initBiome(DEPTH, "Depth", BiomeType.COOL, Type.SPOOKY, Type.COLD);
	}

	private static void initBiome(Biome biome, String name, BiomeType biomeType, Type... types)
	{
		biome.setRegistryName(name);
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, types);
		if(ConfigHandler.overworldInsanityBiome && name.contains("Insanity")) BiomeManager.addBiome(biomeType, new BiomeEntry(biome, ConfigHandler.insanityBiomeWeight));
		if(ConfigHandler.overworldDepthBiome && name.equals("Depth")) BiomeManager.addBiome(biomeType, new BiomeEntry(biome, ConfigHandler.depthBiomeWeight));
		BiomeManager.addSpawnBiome(biome);
	}
}