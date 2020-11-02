package com.fi0x.deepmagic.blocks.slabsstairs;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.IHasModel;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Random;

public class CleanStoneHalfSlab extends SlabBase implements IHasModel
{
    public CleanStoneHalfSlab(String name, Material materialIn)
    {
        super(name, materialIn);
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
        return Item.getItemFromBlock(ModBlocks.CLEAN_STONE_SLAB_HALF);
    }
}
