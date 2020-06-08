package com.fi0x.deepmagic.world.generators;

import java.util.Random;

import com.fi0x.deepmagic.util.IStructure;
import com.fi0x.deepmagic.util.Reference;

import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class ModWorldGenStructure extends WorldGenerator implements IStructure
{
	public String structureName;
	
	public ModWorldGenStructure(String name)
	{
		structureName = name;
	}
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
		System.out.println(structureName + " was created");
		generateStructure(worldIn, position, rand);
		return true;
	}
	
	public void generateStructure(World world, BlockPos pos, Random rand)
	{
		MinecraftServer mcServer = world.getMinecraftServer();
		TemplateManager manager = worldServer.getStructureTemplateManager();
		ResourceLocation location = new ResourceLocation(Reference.MOD_ID, structureName);
		Template template = manager.get(mcServer, location);
		
		if(template != null)
		{
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 3);
			template.addBlocksToWorldChunk(world, pos, settings);
		}
	}
}