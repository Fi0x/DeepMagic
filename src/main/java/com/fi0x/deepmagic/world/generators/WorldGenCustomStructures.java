package com.fi0x.deepmagic.world.generators;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.Reference;
import com.fi0x.deepmagic.world.biome.BiomeInsanity;
import com.fi0x.deepmagic.world.generators.dungeon.Dungeon;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WorldGenCustomStructures implements IWorldGenerator
{
	public static final Dungeon DUNGEON = new Dungeon();

	public static final ModWorldGenStructure MAGE_HOUSE = new ModWorldGenStructure("mage_house");
	public static final ModWorldGenStructure MAGE_HOUSE_SMALL = new ModWorldGenStructure("mage_house_small");
	public static final ModWorldGenStructure INSANITY_ROCK_TROLL_CAVE = new ModWorldGenStructure("insanity_rock_troll_cave");
	public static final ModWorldGenStructure SHRINE = new ModWorldGenStructure("shrine");
	public static final ModWorldGenStructure INSANITY_OASIS = new ModWorldGenStructure("insanity_oasis");
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		if(world.provider.getDimension() == Reference.DIMENSION_ID_INSANITY)
		{
			generateStructure(MAGE_HOUSE_SMALL, world, random, chunkX, chunkZ, -4, 0, 500, BiomeInsanity.class);
			generateStructure(MAGE_HOUSE, world, random, chunkX, chunkZ, -2, 1, 1000, BiomeInsanity.class);
			generateStructure(INSANITY_ROCK_TROLL_CAVE, world, random, chunkX, chunkZ, -1, 0, 500, BiomeInsanity.class);
			generateStructure(SHRINE, world, random, chunkX, chunkZ, 0, 0, 1000, BiomeInsanity.class);
			generateStructure(INSANITY_OASIS, world, random, chunkX, chunkZ, -1, 2, 500, BiomeInsanity.class);

			if(chunkX % 20 == 0 && chunkZ % 20 == 0) DUNGEON.generate(world, random, new BlockPos(chunkX * 16, 20, chunkZ * 16));
		}
	}
	
	private void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int yOffset, int heightDifference, int chance, Class<?>... classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<>(Arrays.asList(classes));
		
		int x = (chunkX * 16) + random.nextInt(15);
		int z = (chunkZ * 16) + random.nextInt(15);
		int y = calculateGenerationHeight(world, x, z)+yOffset;
		BlockPos pos = new BlockPos(x, y, z);
		
		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

		Template template = ((WorldServer) world).getStructureTemplateManager().get(world.getMinecraftServer(), new ResourceLocation(Reference.MOD_ID, ((ModWorldGenStructure) generator).structureName));
		
		if(classesList.contains(biome) && random.nextInt(chance) == 0 && y > 10 && template != null && canSpawnHere(template, pos, heightDifference))
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

	public static boolean canSpawnHere(Template template, BlockPos pos, int heightDifference)
	{
		return isCornerValid(pos, heightDifference)
				&& isCornerValid(pos.add(template.getSize().getX(), 0, 0), heightDifference)
				&& isCornerValid(pos.add(0, 0, template.getSize().getZ()), heightDifference)
				&& isCornerValid(pos.add(template.getSize().getX(), 0, template.getSize().getZ()), heightDifference);
	}
	private static boolean isCornerValid(BlockPos pos, int heightDifference)
	{
		return pos.getY() >= pos.getY() - heightDifference && pos.getY() <= pos.getY() + heightDifference;
	}
}