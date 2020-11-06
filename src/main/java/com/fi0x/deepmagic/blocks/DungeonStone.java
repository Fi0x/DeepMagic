package com.fi0x.deepmagic.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class DungeonStone extends BlockBase
{
	public DungeonStone(String name, Material material)
	{
		super(name, material);
		setSoundType(SoundType.STONE);
		setHardness(5.0F);
		setResistance(5.0F);
		setBlockUnbreakable();
	}
}