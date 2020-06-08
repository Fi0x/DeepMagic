package com.fi0x.deepmagic.world.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.Reference;
import com.fi0x.deepmagic.world.biome.BiomeInsanity;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenCustomStructures implements IWorldGenerator
{
	public static final ModWorldGenStructure MAGE_HOUSE = new ModWorldGenStructure("mage_house");
	public static final ModWorldGenStructure MAGE_HOUSE_SMALL = new ModWorldGenStructure("mage_house_small");
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		if(world.provider.getDimension() == Reference.DIMENSION_ID)
		{
			generateStructure(MAGE_HOUSE_SMALL, world, random, chunkX, chunkZ, -4, 500, BiomeInsanity.class);//increase the chance number to decrease spawn rate
			generateStructure(MAGE_HOUSE, world, random, chunkX, chunkZ, -2, 1000, BiomeInsanity.class);//increase the chance number to decrease spawn rate
		}
	}
	
	private void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int yOffset, int chance, Class<?>... classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));
		
		int x = (chunkX * 16) + random.nextInt(15);
		int z = (chunkZ * 16) + random.nextInt(15);
		int y = calculateGenerationHeight(world, x, z)+yOffset;
		BlockPos pos = new BlockPos(x, y, z);
		
		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
		
		if(classesList.contains(biome) && random.nextInt(chance) == 0 && y > 10) 
		{
			generator.generate(world, random, pos);
		}
	}
	
	private static int calculateGenerationHeight(World world, int x, int z)
	{
		int y = world.getHeight();
		boolean foundGround = false;
		
		while(!foundGround && y-- >= 0)
		{
			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			foundGround = block == ModBlocks.INSANITY_DIRT || block == ModBlocks.INSANITY_GRASS;
		}
		return y;
	}
}