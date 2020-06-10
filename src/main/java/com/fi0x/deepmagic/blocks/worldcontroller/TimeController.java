package com.fi0x.deepmagic.blocks.worldcontroller;

import com.fi0x.deepmagic.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class TimeController extends BlockBase
{
    public TimeController(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.METAL);
        setHardness(3.0F);
        setResistance(15.0F);
        setHarvestLevel("pickaxe", 1);
    }
}