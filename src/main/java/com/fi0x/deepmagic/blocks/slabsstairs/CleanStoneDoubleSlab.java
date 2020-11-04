package com.fi0x.deepmagic.blocks.slabsstairs;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;

public class CleanStoneDoubleSlab extends SlabBase
{
    public CleanStoneDoubleSlab(String name, Material materialIn)
    {
        super(name, materialIn);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
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
        return Item.getItemFromBlock(ModBlocks.CLEAN_STONE_SLAB_HALF);
    }
}
