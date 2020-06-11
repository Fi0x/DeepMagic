package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.util.Reference;
import com.fi0x.deepmagic.world.dimensions.insanity.DimensionInsanity;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionInit
{
	public static final DimensionType INSANITY = DimensionType.register("Insanity", "_insanity", Reference.DIMENSION_ID_INSANITY, DimensionInsanity.class, false);
	
	public static void registerDimensions()
	{
		DimensionManager.registerDimension(Reference.DIMENSION_ID_INSANITY, INSANITY);
	}
}