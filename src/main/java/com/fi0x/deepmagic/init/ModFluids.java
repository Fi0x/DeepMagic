package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.fluids.InsanityLava;
import com.fi0x.deepmagic.fluids.InsanityWater;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids
{
    public static final Fluid INSANITY_WATER = new InsanityWater("insanity_water", new ResourceLocation("deepmagic:blocks/insanity_water_still"), new ResourceLocation("deepmagic:blocks/insanity_water_flow"));
    public static final Fluid INSANITY_LAVA = new InsanityLava("insanity_lava", new ResourceLocation("deepmagic:blocks/insanity_lava_still"), new ResourceLocation("deepmagic:blocks/insanity_lava_flow"));

    public static void registerFluids()
    {
        registerFluid(INSANITY_WATER);
        registerFluid(INSANITY_LAVA);
    }
    private static void registerFluid(Fluid fluid)
    {
        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);
    }
}