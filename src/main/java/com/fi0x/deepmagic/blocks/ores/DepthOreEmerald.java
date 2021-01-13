package com.fi0x.deepmagic.blocks.ores;

import com.fi0x.deepmagic.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class DepthOreEmerald extends BlockBase
{
    public DepthOreEmerald(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(4.1F);
        setResistance(6.1F);
        setHarvestLevel("pickaxe", 2);
    }
}