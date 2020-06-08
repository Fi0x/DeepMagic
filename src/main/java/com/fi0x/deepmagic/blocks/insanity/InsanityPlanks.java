package com.fi0x.deepmagic.blocks.insanity;

import com.fi0x.deepmagic.blocks.BlockBase;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class InsanityPlanks extends BlockBase
{
	public InsanityPlanks(String name, Material material)
	{
		super(name, material);
		setSoundType(SoundType.WOOD);
		setHardness(2.1F);
		setResistance(5.1F);
	}
}