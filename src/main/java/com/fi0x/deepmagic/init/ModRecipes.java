package com.fi0x.deepmagic.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes
{
	public static void init()
	{
		GameRegistry.addSmelting(ModBlocks.DEEP_CRYSTAL_ORE, new ItemStack(ModItems.DEEP_CRYSTAL, 1), 1F);
		GameRegistry.addSmelting(ModItems.DEEP_CRYSTAL_POWDER, new ItemStack(ModItems.DEEP_CRYSTAL, 1), 1F);
	}
}