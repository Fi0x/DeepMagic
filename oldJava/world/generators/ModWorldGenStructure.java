package com.fi0x.deepmagic.world.generators;

import com.fi0x.deepmagic.util.IStructure;
import com.fi0x.deepmagic.world.GenerationHelper;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

public class ModWorldGenStructure extends WorldGenerator implements IStructure
{
	public String structureName;
	
	public ModWorldGenStructure(String name)
	{
		structureName = name;
	}
	
	@Override
	public boolean generate(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos pos)
	{
		Rotation rotation = Rotation.values()[rand.nextInt(4)];
		return GenerationHelper.templatePlacer(world, rand, pos, structureName, rotation);
	}
}