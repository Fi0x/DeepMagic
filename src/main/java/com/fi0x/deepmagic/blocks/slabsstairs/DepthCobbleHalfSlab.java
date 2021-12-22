package com.fi0x.deepmagic.blocks.slabsstairs;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.util.IHasModel;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;

public class DepthCobbleHalfSlab extends SlabBase implements IHasModel
{
    public DepthCobbleHalfSlab(String name, Material materialIn, BlockSlab doubleSlab)
    {
        super(name, materialIn, doubleSlab);
        setCreativeTab(DeepMagicTab.BLOCKS);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public boolean isDouble()
    {
        return false;
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.DEPTH_COBBLE_SLAB_HALF);
    }
}
