package com.fi0x.deepmagic.blocks.insanity;

import com.fi0x.deepmagic.blocks.BlockBase;
import com.fi0x.deepmagic.init.ModBlocks;
import com.google.common.base.Predicate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import java.util.Random;

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

	@Nonnull
	@Override
	public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
	{
		return Item.getItemFromBlock(ModBlocks.INSANITY_COBBLE);
	}

	@Override
	public boolean isReplaceableOreGen(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull Predicate<IBlockState> target)
	{
		return true;
	}
}