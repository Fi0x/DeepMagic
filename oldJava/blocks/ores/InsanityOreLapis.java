package com.fi0x.deepmagic.blocks.ores;

import com.fi0x.deepmagic.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import java.util.Random;

public class InsanityOreLapis extends BlockBase
{
	public InsanityOreLapis(String name, Material material)
	{
		super(name, material);
		setSoundType(SoundType.STONE);
		setHardness(3.1F);
		setResistance(5.1F);
		setHarvestLevel("pickaxe", 2);
	}
	
	@Nonnull
	@Override
	public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
	{
		return Items.DYE;
	}
	@Override
	public int quantityDropped(Random rand)
	{
		return 8;
	}
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
	{
		return 2;
	}
	@Override
	public int damageDropped(IBlockState state)
	{
		return EnumDyeColor.BLUE.getDyeDamage();
	}
}