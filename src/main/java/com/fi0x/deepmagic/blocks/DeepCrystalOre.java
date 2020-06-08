package com.fi0x.deepmagic.blocks;

import java.util.Random;

import com.fi0x.deepmagic.init.ModItems;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class DeepCrystalOre extends BlockBase
{
	public DeepCrystalOre(String name, Material material)
	{
		super(name, material);
		setSoundType(SoundType.STONE);
		setHardness(3.0F);
		setResistance(5.0F);
		setHarvestLevel("pickaxe", 2);
		setLightLevel(0.4F);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ModItems.DEEP_CRYSTAL_POWDER;
	}
	
	@Override
	public int quantityDropped(Random rand)
	{
		return rand.nextInt((4) + 1);
	}
}