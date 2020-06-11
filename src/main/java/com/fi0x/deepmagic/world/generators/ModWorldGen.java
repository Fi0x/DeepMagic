package com.fi0x.deepmagic.world.generators;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class ModWorldGen implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		if(world.provider.getDimension() == 0)
		{
			generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		} else if(world.provider.getDimension() == -1)
		{
			generateNether(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		} else if(world.provider.getDimension() == 1)
		{
			generateEnd(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		} else if(world.provider.getDimension() == Reference.DIMENSION_ID_DEEPDARK)
		{
			generateDeepDark(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		} else if(world.provider.getDimension() == Reference.DIMENSION_ID_TWILIGHTFOREST)
		{
			generateTwilightForest(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		} else if(world.provider.getDimension() == Reference.DIMENSION_ID_AROMAMININGWORLD)
		{
			generateAromaMiningWorld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		} else if(world.provider.getDimension() == Reference.DIMENSION_ID_INSANITY)
		{
			generateInsanityDimension(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		} else
		{
			generateDefaultDimension(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}
	
	private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		generateOre(ModBlocks.DEEP_CRYSTAL_ORE.getDefaultState(), world, random, chunkX*16, chunkZ*16, 1, 12, random.nextInt(4) + 2, 10);
	}
	
	private void generateNether(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		generateOre(ModBlocks.DEEP_CRYSTAL_NETHER_ORE.getDefaultState(), world, random, chunkX*16, chunkZ*16, 1, 200, random.nextInt(8) + 4, 10, Blocks.NETHERRACK);
	}
	
	private void generateEnd(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		generateOre(ModBlocks.DEEP_CRYSTAL_END_ORE.getDefaultState(), world, random, chunkX*16, chunkZ*16, 1, 100, random.nextInt(4) + 2, 20, Blocks.END_STONE);
	}
	
	private void generateDeepDark(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		generateOre(ModBlocks.DEEP_CRYSTAL_ORE.getDefaultState(), world, random, chunkX*16, chunkZ*16, 1, 64, random.nextInt(8) + 4, 20);
	}
	
	private void generateTwilightForest(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		generateOre(ModBlocks.DEEP_CRYSTAL_ORE.getDefaultState(), world, random, chunkX*16, chunkZ*16, 1, 12, random.nextInt(4) + 2, 8);
	}
	
	private void generateAromaMiningWorld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		generateOre(ModBlocks.DEEP_CRYSTAL_ORE.getDefaultState(), world, random, chunkX*16, chunkZ*16, 1, 64, 1, 6);
	}
	
	private void generateInsanityDimension(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		generateOre(ModBlocks.DEEP_CRYSTAL_ORE.getDefaultState(), world, random, chunkX*16, chunkZ*16, 1, 64, 8, 20);
	}
	
	private void generateDefaultDimension(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		generateOre(ModBlocks.DEEP_CRYSTAL_ORE.getDefaultState(), world, random, chunkX*16, chunkZ*16, 1, 12, 1, 10);
	}
	
	private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances)
	{
		int deltaY = maxY-minY;
		
		for(int i = 0; i < chances; i++)
		{
			BlockPos pos = new BlockPos(x+random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));
			WorldGenMinable generator = new WorldGenMinable(ore, size);
			generator.generate(world, random, pos);
		}
	}
	
	private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances, Block blockType)
	{
		int deltaY = maxY-minY;
		
		for(int i = 0; i < chances; i++)
		{
			BlockPos pos = new BlockPos(x+random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));
			WorldGenMinable generator = new WorldGenMinable(ore, size, BlockMatcher.forBlock(blockType));
			generator.generate(world, random, pos);
		}
	}
}