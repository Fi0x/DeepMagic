package com.fi0x.deepmagic.blocks.rituals;

import com.fi0x.deepmagic.blocks.rituals.tile.TileEntityRitualSpawnDenial;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RitualSpawnDenial extends RitualStone implements ITileEntityProvider
{
    public RitualSpawnDenial(String name, Material material)
    {
        super(name, material);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileEntityRitualSpawnDenial();
    }
}