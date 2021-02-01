package com.fi0x.deepmagic.blocks.rituals;

import com.fi0x.deepmagic.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RitualBuildBlock extends BlockBase
{
    public RitualBuildBlock(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(5.0F);
        setHarvestLevel("pickaxe", 0);
    }
}
