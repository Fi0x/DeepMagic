package com.fi0x.deepmagic.blocks.slabsstairs;

import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

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
        if(!this.isDouble()) return 0;
        return state.getValue(HALF).ordinal() + 1;
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, HALF);
    }
}