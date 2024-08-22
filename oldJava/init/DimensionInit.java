package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.world.dimensions.depth.DimensionDepth;
import com.fi0x.deepmagic.world.dimensions.insanity.DimensionInsanity;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionInit
{
	public static final DimensionType INSANITY = DimensionType.register("Insanity", "_insanity", ConfigHandler.dimensionIdInsanityID, DimensionInsanity.class, false);
	public static final DimensionType DEPTH = DimensionType.register("Depth", "_depth", ConfigHandler.dimensionIdDepthID, DimensionDepth.class, false);

	public static void registerDimensions()
	{
		DimensionManager.registerDimension(ConfigHandler.dimensionIdInsanityID, INSANITY);
		DimensionManager.registerDimension(ConfigHandler.dimensionIdDepthID, DEPTH);
	}
}