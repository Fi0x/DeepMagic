package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.blocks.AltarOfKnowledge;
import com.fi0x.deepmagic.blocks.BlockFluid;
import com.fi0x.deepmagic.blocks.DeepCrystalBlock;
import com.fi0x.deepmagic.blocks.DeepCrystalOre;
import com.fi0x.deepmagic.blocks.insanity.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks
{
	public static final List<Block> BLOCKS = new ArrayList<>();
	
	public static final Block DEEP_CRYSTAL_ORE = new DeepCrystalOre("deep_crystal_ore", Material.ROCK);
	public static final Block DEEP_CRYSTAL_NETHER_ORE = new DeepCrystalOre("deep_crystal_nether_ore", Material.ROCK);
	public static final Block DEEP_CRYSTAL_END_ORE = new DeepCrystalOre("deep_crystal_end_ore", Material.ROCK);
	public static final Block DEEP_CRYSTAL_BLOCK = new DeepCrystalBlock("deep_crystal_block", Material.IRON);
	
	public static final Block INSANITY_STONE = new InsanityStone("insanity_stone", Material.ROCK);
	public static final Block INSANITY_DIRT = new InsanityDirt("insanity_dirt", Material.GROUND);
	public static final Block INSANITY_GRASS = new InsanityGrass("insanity_grass", Material.GRASS);
	public static final Block INSANITY_PLANKS = new InsanityPlanks("insanity_planks", Material.WOOD);
	public static final Block INSANITY_FLOWER = new InsanityFlower("insanity_flower", Material.PLANTS);
	public static final Block INSANITY_LOG = new InsanityLog("insanity_log");
	public static final Block INSANITY_LEAVES = new InsanityLeaves("insanity_leaves");
	public static final Block INSANITY_WATER = new BlockFluid("insanity_water", ModFluids.INSANITY_WATER, Material.WATER);

	public static final Block ALTAR_OF_KNOWLEDGE = new AltarOfKnowledge("altar_of_knowledge", Material.ROCK);
}