package com.fi0x.deepmagic.blocks.insanity;

import com.fi0x.deepmagic.blocks.BlockBase;
import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Random;

public class BrightInsanityStone extends BlockBase
{
	public BrightInsanityStone(String name, Material material)
	{
		super(name, material);
		setSoundType(SoundType.METAL);
		setHardness(1.6F);
		setResistance(8F);
		setHarvestLevel("pickaxe", 2);
		setLightLevel(0.8F);
	}
	
	@Nonnull
	@Override
	public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
	{
		return Item.getItemFromBlock(ModBlocks.INSANITY_COBBLE);
	}
}