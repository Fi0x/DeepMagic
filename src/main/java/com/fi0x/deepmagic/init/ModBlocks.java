package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.blocks.AltarOfKnowledge;
import com.fi0x.deepmagic.blocks.BlockFluid;
import com.fi0x.deepmagic.blocks.DeepCrystalBlock;
import com.fi0x.deepmagic.blocks.DeepCrystalOre;
import com.fi0x.deepmagic.blocks.effectstones.AttackStone;
import com.fi0x.deepmagic.blocks.effectstones.DefenceStone;
import com.fi0x.deepmagic.blocks.effectstones.LevitationStone;
import com.fi0x.deepmagic.blocks.effectstones.PotionEffectStone;
import com.fi0x.deepmagic.blocks.insanity.*;
import com.fi0x.deepmagic.blocks.slabs.DoubleSlabBase;
import com.fi0x.deepmagic.blocks.slabs.HalfSlabBase;
import com.fi0x.deepmagic.blocks.worldcontroller.TimeController;
import com.fi0x.deepmagic.blocks.worldcontroller.WeatherController;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.init.MobEffects;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks
{
	public static final List<Block> BLOCKS = new ArrayList<>();
	
	public static final Block DEEP_CRYSTAL_ORE = new DeepCrystalOre("deep_crystal_ore", Material.ROCK);
	public static final Block DEEP_CRYSTAL_NETHER_ORE = new DeepCrystalOre("deep_crystal_nether_ore", Material.ROCK);
	public static final Block DEEP_CRYSTAL_END_ORE = new DeepCrystalOre("deep_crystal_end_ore", Material.ROCK);
	public static final Block DEEP_CRYSTAL_BLOCK = new DeepCrystalBlock("deep_crystal_block", Material.IRON);
	public static final Block DEFENCE_STONE = new DefenceStone("defence_stone", Material.ROCK);
	public static final Block ATTACK_STONE = new AttackStone("attack_stone", Material.ROCK);
	public static final Block SPEED_STONE = new PotionEffectStone("speed_stone", Material.ROCK, MobEffects.SPEED, 3, 3);
	public static final Block HEAL_STONE = new PotionEffectStone("heal_stone", Material.ROCK, MobEffects.REGENERATION, 2, 3);
	public static final Block VISION_STONE = new PotionEffectStone("vision_stone", Material.ROCK, MobEffects.NIGHT_VISION, 11, 1);
	public static final Block SATURATION_STONE = new PotionEffectStone("saturation_stone", Material.ROCK, MobEffects.SATURATION, 60, 3);
	public static final Block LEVITATION_STONE = new LevitationStone("levitation_stone", Material.ROCK);
	public static final BlockSlab CLEAN_STONE_SLAB_DOUBLE = new DoubleSlabBase("clean_stone_slab_double", Material.ROCK);
	public static final BlockSlab CLEAN_STONE_SLAB_HALF = new HalfSlabBase("clean_stone_slab_half", Material.ROCK);
	
	public static final Block INSANITY_STONE = new InsanityStone("insanity_stone", Material.ROCK);
	public static final Block INSANITY_DIRT = new InsanityDirt("insanity_dirt", Material.GROUND);
	public static final Block INSANITY_GRASS = new InsanityGrass("insanity_grass", Material.GRASS);
	public static final Block INSANITY_PLANKS = new InsanityPlanks("insanity_planks", Material.WOOD);
	public static final Block INSANITY_FLOWER = new InsanityFlower("insanity_flower", Material.PLANTS);
	public static final Block INSANITY_LOG = new InsanityLog("insanity_log");
	public static final Block INSANITY_LEAVES = new InsanityLeaves("insanity_leaves");
	public static final Block INSANITY_SAPLING = new InsanitySapling("insanity_sapling", Material.PLANTS);
	public static final Block INSANITY_WATER = new BlockFluid("insanity_water", ModFluids.INSANITY_WATER, Material.WATER);

	public static final Block ALTAR_OF_KNOWLEDGE = new AltarOfKnowledge("altar_of_knowledge", Material.ROCK);
	public static final Block WEATHER_CONTROLLER = new WeatherController("weather_controller", Material.IRON);
	public static final Block TIME_CONTROLLER = new TimeController("time_controller", Material.IRON);
}