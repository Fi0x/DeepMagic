package com.fi0x.deepmagic.blocks.ores;

import com.fi0x.deepmagic.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class InsanityOreEmerald extends BlockBase
{
	public InsanityOreEmerald(String name, Material material)
	{
		super(name, material);
		setSoundType(SoundType.STONE);
		setHardness(3.1F);
		setResistance(5.1F);
		setHarvestLevel("pickaxe", 2);
	}
}