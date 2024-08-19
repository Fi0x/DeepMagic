package com.fi0x.deepmagic.fluids;

import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class BaseFluid extends Fluid
{
    public BaseFluid(String name, ResourceLocation still, ResourceLocation flowing)
    {
        super(name, still, flowing);
        setUnlocalizedName(name);
        setLuminosity(0);
        setDensity(1000);
        setTemperature(300);
        setViscosity(1000);
        setRarity(EnumRarity.COMMON);
    }

    @Override
    public boolean doesVaporize(FluidStack fluidStack)
    {
        return super.doesVaporize(fluidStack);
    }
    @Override
    public SoundEvent getFillSound()
    {
        return super.getFillSound();
    }

    @Override
    public SoundEvent getEmptySound()
    {
        return super.getEmptySound();
    }
}