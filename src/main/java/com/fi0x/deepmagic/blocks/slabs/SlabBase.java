package com.fi0x.deepmagic.blocks.slabs;

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

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class SlabBase extends BlockSlab
{
    public SlabBase(String name, Material materialIn)
    {
        super(materialIn);
        setUnlocalizedName(name);
        setRegistryName(name);

        IBlockState state = this.blockState.getBaseState();
        if(!this.isDouble()) state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
        setDefaultState(state);
        this.useNeighborBrightness = true;

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(int meta)
    {
        return this.getUnlocalizedName();
    }

    @Nonnull
    @Override
    public IProperty<?> getVariantProperty()
    {
        return HALF;
    }

    @Nonnull
    @Override
    public Comparable<?> getTypeForItem(@Nonnull ItemStack stack)
    {
        return EnumBlockHalf.BOTTOM;
    }

    @Override
    public int damageDropped(@Nonnull IBlockState state)
    {
        return 0;
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        if(!this.isDouble()) return this.getDefaultState().withProperty(HALF, EnumBlockHalf.values()[meta % EnumBlockHalf.values().length]);
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state)
    {
        if(this.isDouble()) return 0;
        return state.getValue(HALF).ordinal() + 1;
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.CLEAN_STONE_SLAB_HALF);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{HALF});
    }
}