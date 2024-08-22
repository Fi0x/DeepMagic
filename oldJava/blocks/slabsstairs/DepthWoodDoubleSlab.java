package com.fi0x.deepmagic.blocks.slabsstairs;

import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class DepthWoodDoubleSlab extends SlabBase
{
    public DepthWoodDoubleSlab(String name, Material materialIn)
    {
        super(name, materialIn);
    }

    @Override
    public boolean isDouble()
    {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getItem(@Nonnull World p_185473_1_, @Nonnull BlockPos p_185473_2_, @Nonnull IBlockState p_185473_3_)
    {
        return new ItemStack(ModBlocks.DEPTH_WOOD_SLAB_HALF);
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.DEPTH_WOOD_SLAB_HALF);
    }
}
