package com.fi0x.deepmagic.util.recipes;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes
{
	public static void init()
	{
		GameRegistry.addSmelting(ModBlocks.DEEP_CRYSTAL_ORE, new ItemStack(ModItems.DEEP_CRYSTAL, 1), 1F);
		GameRegistry.addSmelting(ModBlocks.DEEP_CRYSTAL_NETHER_ORE, new ItemStack(ModItems.DEEP_CRYSTAL, 1), 1F);
		GameRegistry.addSmelting(ModBlocks.DEEP_CRYSTAL_END_ORE, new ItemStack(ModItems.DEEP_CRYSTAL, 1), 1F);
		GameRegistry.addSmelting(ModItems.DEEP_CRYSTAL_POWDER, new ItemStack(ModItems.DEEP_CRYSTAL, 1), 1F);

		GameRegistry.addSmelting(ModBlocks.INSANITY_COBBLE, new ItemStack(ModBlocks.INSANITY_STONE, 1), 1F);
		GameRegistry.addSmelting(ModBlocks.INSANITY_IRON_ORE, new ItemStack(Items.IRON_INGOT, 1), 1F);
		GameRegistry.addSmelting(ModBlocks.INSANITY_GOLD_ORE, new ItemStack(Items.GOLD_INGOT, 1), 1F);
		GameRegistry.addSmelting(ModBlocks.INSANITY_DIAMOND_ORE, new ItemStack(Items.DIAMOND, 1), 1F);
		GameRegistry.addSmelting(ModBlocks.INSANITY_EMERALD_ORE, new ItemStack(Items.EMERALD, 1), 1F);
		GameRegistry.addSmelting(ModBlocks.INSANITY_DEEP_CRYSTAL_ORE, new ItemStack(ModItems.DEEP_CRYSTAL, 1), 1F);

		GameRegistry.addSmelting(ModBlocks.DEPTH_COBBLE, new ItemStack(ModBlocks.DEPTH_STONE, 1), 1F);
		GameRegistry.addSmelting(ModBlocks.DEPTH_IRON_ORE, new ItemStack(Items.IRON_INGOT, 1), 1F);
		GameRegistry.addSmelting(ModBlocks.DEPTH_GOLD_ORE, new ItemStack(Items.GOLD_INGOT, 1), 1F);
		GameRegistry.addSmelting(ModBlocks.DEPTH_DIAMOND_ORE, new ItemStack(Items.DIAMOND, 1), 1F);
		GameRegistry.addSmelting(ModBlocks.DEPTH_EMERALD_ORE, new ItemStack(Items.EMERALD, 1), 1F);
		GameRegistry.addSmelting(ModBlocks.DEEP_CRYSTAL_ORE_COMPRESSED, new ItemStack(ModItems.DEEP_CRYSTAL, 2), 1F);

		GameRegistry.addSmelting(ModItems.RAW_COCKROACH, new ItemStack(ModItems.COOKED_COCKROACH, 1), 1F);
		GameRegistry.addSmelting(ModItems.RAW_WORM, new ItemStack(ModItems.COOKED_WORM, 1), 1F);
		GameRegistry.addSmelting(Items.ROTTEN_FLESH, new ItemStack(ModItems.DRY_FLESH, 1), 1F);
		GameRegistry.addSmelting(ModItems.WHEAT_FLOUR, new ItemStack(Items.BREAD, 1), 0F);
		GameRegistry.addSmelting(ModBlocks.INSANITY_LOG, new ItemStack(Items.COAL, 1), 1F);
	}
}