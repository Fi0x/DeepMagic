package com.fi0x.deepmagic.blocks.slabs;

import net.minecraft.block.material.Material;

public class DoubleSlabBase extends SlabBase
{
    public DoubleSlabBase(String name, Material materialIn)
    {
        super(name, materialIn);
    }

    @Override
    public boolean isDouble()
    {
        return true;
    }
}
