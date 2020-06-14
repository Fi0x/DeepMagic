package com.fi0x.deepmagic.blocks.slabs;

import net.minecraft.block.material.Material;

public class HalfSlabBase extends SlabBase
{
    public HalfSlabBase(String name, Material materialIn)
    {
        super(name, materialIn);
    }

    @Override
    public boolean isDouble()
    {
        return false;
    }
}
