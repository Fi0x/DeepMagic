package com.fi0x.deepmagic.blocks.depth;

import com.fi0x.deepmagic.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class DepthPlanks extends BlockBase
{
    public DepthPlanks(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.WOOD);
        setHardness(3.1F);
        setResistance(6.1F);
    }
}