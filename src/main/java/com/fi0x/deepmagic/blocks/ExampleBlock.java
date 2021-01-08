package com.fi0x.deepmagic.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Random;

public class ExampleBlock extends BlockBase
{
	public ExampleBlock(String name, Material material)
	{
        super(name, material);
        setSoundType(SoundType.METAL);
        setHardness(5.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 0);
        setLightLevel(1F);
        setLightOpacity(1);
        setBlockUnbreakable();
        setDefaultSlipperiness(0.6F);
    }
	
	@Nonnull
	@Override
	public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
	{
		return Item.getItemFromBlock(Blocks.COBBLESTONE);
	}
	@Override
	public int quantityDropped(@Nonnull Random random)
	{
		return 64;
	}
}