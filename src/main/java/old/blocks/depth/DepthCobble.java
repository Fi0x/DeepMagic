package com.fi0x.deepmagic.blocks.depth;

import com.fi0x.deepmagic.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class DepthCobble extends BlockBase
{
    public DepthCobble(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(2.5F);
        setResistance(8F);
        setHarvestLevel("pickaxe", 2);
    }
}