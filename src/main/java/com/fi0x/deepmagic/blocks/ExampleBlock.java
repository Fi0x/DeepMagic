package com.fi0x.deepmagic.blocks;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class ExampleBlock extends BlockBase
{
	public ExampleBlock(String name, Material material)
	{
		super(name, material);
		setSoundType(SoundType.METAL);
		setHardness(5.0F);
		setResistance(5.0F);
		setHarvestLevel("pickaxe", 0);
		setLightLevel(15.0F);
		setLightOpacity(1);
		setBlockUnbreakable();
		setDefaultSlipperiness(1);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(Blocks.COBBLESTONE);
	}
	@Override
	public int quantityDropped(Random random)
	{
		return 64;
	}
}