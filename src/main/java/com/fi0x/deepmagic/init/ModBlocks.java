package com.fi0x.deepmagic.init;

import java.util.ArrayList;
import java.util.List;

import com.fi0x.deepmagic.blocks.DeepCrystalBlock;
import com.fi0x.deepmagic.blocks.DeepCrystalOre;
import com.fi0x.deepmagic.blocks.insanity.InsanityDirt;
import com.fi0x.deepmagic.blocks.insanity.InsanityFlower;
import com.fi0x.deepmagic.blocks.insanity.InsanityGrass;
import com.fi0x.deepmagic.blocks.insanity.InsanityPlanks;
import com.fi0x.deepmagic.blocks.insanity.InsanityStone;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block DEEP_CRYSTAL_ORE = new DeepCrystalOre("deep_crystal_ore", Material.ROCK);
	public static final Block DEEP_CRYSTAL_NETHER_ORE = new DeepCrystalOre("deep_crystal_nether_ore", Material.ROCK);
	public static final Block DEEP_CRYSTAL_END_ORE = new DeepCrystalOre("deep_crystal_end_ore", Material.ROCK);
	public static final Block DEEP_CRYSTAL_BLOCK = new DeepCrystalBlock("deep_crystal_block", Material.IRON);
	
	public static final Block INSANITY_STONE = new InsanityStone("insanity_stone", Material.ROCK);
	public static final Block INSANITY_DIRT = new InsanityDirt("insanity_dirt", Material.GROUND);
	public static final Block INSANITY_GRASS = new InsanityGrass("insanity_grass", Material.GRASS);
	public static final Block INSANITY_PLANKS = new InsanityPlanks("insanity_planks", Material.WOOD);
	public static final Block INSANITY_FLOWER = new InsanityFlower("insanity_flower", Material.PLANTS);
	
}