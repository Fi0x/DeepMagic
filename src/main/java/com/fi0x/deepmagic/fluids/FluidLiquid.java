package com.fi0x.deepmagic.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidLiquid extends Fluid
{
    public FluidLiquid(String name, ResourceLocation still, ResourceLocation flowing)
    {
        super(name, still, flowing);
        setUnlocalizedName(name);
    }
}