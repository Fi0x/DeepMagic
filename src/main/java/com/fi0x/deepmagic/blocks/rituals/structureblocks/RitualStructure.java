package com.fi0x.deepmagic.blocks.rituals.structureblocks;

import com.fi0x.deepmagic.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RitualStructure extends BlockBase
{
    public RitualStructure(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(5.0F);
        setHarvestLevel("pickaxe", 0);
    }

    public int getStructureType()
    {
        return 0;
    }
}