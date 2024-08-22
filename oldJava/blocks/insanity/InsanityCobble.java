package com.fi0x.deepmagic.blocks.insanity;

import com.fi0x.deepmagic.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class InsanityCobble extends BlockBase
{
	public InsanityCobble(String name, Material material)
	{
		super(name, material);
		setSoundType(SoundType.STONE);
		setHardness(1.5F);
		setResistance(7F);
		setHarvestLevel("pickaxe", 1);
	}
}