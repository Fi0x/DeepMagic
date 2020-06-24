package com.fi0x.deepmagic.blocks;

import com.fi0x.deepmagic.init.ModItems;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Random;

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
	
	@Nonnull
	@Override
	public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
	{
		return ModItems.DEEP_CRYSTAL_POWDER;
	}
	
	@Override
	public int quantityDropped(Random rand)
	{
		return rand.nextInt((4) + 1);
	}
}