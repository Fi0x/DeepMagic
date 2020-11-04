package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.blocks.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class BlockTileEntity<TE extends TileEntity> extends BlockBase
{
    public BlockTileEntity(String name, Material material)
    {
        super(name, material);
    }

    public abstract Class<TE> getTileEntityClass();

    public TE getTileEntity(IBlockAccess world, BlockPos pos)
    {
        return (TE)world.getTileEntity(pos);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public abstract TileEntity createTileEntity(World world, IBlockState state);
}