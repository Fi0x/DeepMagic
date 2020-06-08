package com.fi0x.deepmagic.blocks.insanity;

import java.util.Random;

import com.fi0x.deepmagic.blocks.BlockBase;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class InsanityStone extends BlockBase
{
	public InsanityStone(String name, Material material)
	{
		super(name, material);
		setSoundType(SoundType.STONE);
		setHardness(1.6F);
		setResistance(11F);
		setHarvestLevel("pickaxe", 2);
	}
	
	@Override
	public int quantityDropped(Random random)
	{
		return random.nextInt(3);
	}
}