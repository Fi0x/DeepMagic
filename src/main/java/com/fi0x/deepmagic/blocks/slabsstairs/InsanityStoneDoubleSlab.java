package com.fi0x.deepmagic.blocks.slabsstairs;

import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Random;

public class InsanityStoneDoubleSlab extends SlabBase
{
    public InsanityStoneDoubleSlab(String name, Material materialIn)
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
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.INSANITY_STONE_SLAB_HALF);
    }
}
