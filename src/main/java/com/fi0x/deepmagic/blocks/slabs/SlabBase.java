package com.fi0x.deepmagic.blocks.slabs;

import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.Random;

public abstract class SlabBase extends BlockSlab
{
    public SlabBase(String name, Material materialIn)
    {
        super(materialIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(DeepMagicTab.BLOCKS);

        IBlockState state = this.blockState.getBaseState();
        if(!this.isDouble()) state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
        setDefaultState(state);
        this.useNeighborBrightness = true;

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public String getUnlocalizedName(int meta)
    {
        return this.getUnlocalizedName();
    }

    @Override
    public IProperty<?> getVariantProperty()
    {
        return HALF;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return EnumBlockHalf.BOTTOM;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        if(!this.isDouble()) return this.getDefaultState().withProperty(HALF, EnumBlockHalf.values()[meta % EnumBlockHalf.values().length]);
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        if(this.isDouble()) return 0;
        return ((EnumBlockHalf)state.getValue(HALF)).ordinal() + 1;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.CLEAN_STONE_SLAB_HALF);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{HALF});
    }
}