package com.fi0x.deepmagic.fluids;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.FluidStack;

public class InsanityWater extends BaseFluid
{
    public InsanityWater(String name, ResourceLocation still, ResourceLocation flowing)
    {
        super(name, still, flowing);
        setLuminosity(1);
        setDensity(100);
        setTemperature(350);
        setViscosity(750);
        setRarity(EnumRarity.UNCOMMON);
    }

    @Override
    public boolean doesVaporize(FluidStack fluidStack)
    {
        return false;
    }
    @Override
    public SoundEvent getFillSound()
    {
        return SoundEvents.ITEM_BUCKET_FILL;
    }

    @Override
    public SoundEvent getEmptySound()
    {
        return SoundEvents.ITEM_BUCKET_EMPTY;
    }
}