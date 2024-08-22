package com.fi0x.deepmagic.blocks.slabsstairs;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Objects;

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

    public SlabBase(String name, Material materialIn, BlockSlab doubleSlab)
    {
        this(name, materialIn);
        ModItems.ITEMS.add(new ItemSlab(this, this, doubleSlab).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
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
        IBlockState iblockstate = this.getDefaultState();
        iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        return iblockstate;
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state)
    {
        int i = 0;
        if(state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) i |= 8;
        return i;
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, HALF);
    }
}