package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.world.biomes.BiomeDepth;
import com.fi0x.deepmagic.world.biomes.insanity.*;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;

public class BiomeInit
{
	public static final Biome INSANITY_PLAINS = new BiomeInsanityPlains();
	public static final Biome INSANITY_FOREST_SMALL = new BiomeInsanityForestSmall();
	public static final Biome INSANITY_FOREST_LARGE = new BiomeInsanityForestLarge();
	public static final Biome INSANITY_FOREST_MIXED = new BiomeInsanityForestMixed();
	public static final Biome INSANITY_HILLS = new BiomeInsanityHills();

	public static final Biome DEPTH = new BiomeDepth();

	public static void registerBiomes()
	{
		//TODO: Adjust biome types
		initBiome(INSANITY_PLAINS, "InsanityPlains", BiomeType.WARM, Type.HOT, Type.SPARSE, Type.DRY, Type.SAVANNA, Type.SPOOKY);
		initBiome(INSANITY_FOREST_SMALL, "InsanityForestSmall", BiomeType.WARM, Type.DRY);
		initBiome(INSANITY_FOREST_LARGE, "InsanityForestLarge", BiomeType.WARM, Type.DRY);
		initBiome(INSANITY_FOREST_MIXED, "InsanityForestMixed", BiomeType.WARM, Type.DRY);
		initBiome(INSANITY_HILLS, "InsanityHills", BiomeType.WARM, Type.DRY);

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

	public static ArrayList<Biome> getInsanityBiomes()
	{
		ArrayList<Biome> biomes = new ArrayList<>();

		biomes.add(INSANITY_PLAINS);
		biomes.add(INSANITY_FOREST_SMALL);
		biomes.add(INSANITY_FOREST_LARGE);
		biomes.add(INSANITY_FOREST_MIXED);
		biomes.add(INSANITY_HILLS);

		return biomes;
	}
}