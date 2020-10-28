package com.fi0x.deepmagic.fluids;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.FluidStack;

public class InsanityLava extends BaseFluid
{
    public InsanityLava(String name, ResourceLocation still, ResourceLocation flowing)
    {
        super(name, still, flowing);
        setLuminosity(10);
        setDensity(500);
        setTemperature(1000);
        setViscosity(3000);
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