package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.blocks.*;
import com.fi0x.deepmagic.blocks.depth.*;
import com.fi0x.deepmagic.blocks.effectstones.AttackStone;
import com.fi0x.deepmagic.blocks.effectstones.DefenceStone;
import com.fi0x.deepmagic.blocks.effectstones.LevitationStone;
import com.fi0x.deepmagic.blocks.effectstones.PotionEffectStone;
import com.fi0x.deepmagic.blocks.insanity.*;
import com.fi0x.deepmagic.blocks.mana.*;
import com.fi0x.deepmagic.blocks.ores.*;
import com.fi0x.deepmagic.blocks.partial.DwarfLamp;
import com.fi0x.deepmagic.blocks.partial.MagicLight;
import com.fi0x.deepmagic.blocks.partial.Rune;
import com.fi0x.deepmagic.blocks.rituals.RitualQuarry;
import com.fi0x.deepmagic.blocks.rituals.RitualSpawnDenial;
import com.fi0x.deepmagic.blocks.rituals.RitualTime;
import com.fi0x.deepmagic.blocks.rituals.RitualWeather;
import com.fi0x.deepmagic.blocks.rituals.structureblocks.RitualStructure;
import com.fi0x.deepmagic.blocks.slabsstairs.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks
{
	public static final List<Block> BLOCKS = new ArrayList<>();

	public static final Block DEEP_CRYSTAL_ORE = new DeepCrystalOre("deep_crystal_ore", Material.ROCK);
	public static final Block DEEP_CRYSTAL_NETHER_ORE = new DeepCrystalOre("deep_crystal_nether_ore", Material.ROCK);
	public static final Block DEEP_CRYSTAL_END_ORE = new DeepCrystalOre("deep_crystal_end_ore", Material.ROCK);
	public static final Block DEEP_CRYSTAL_BLOCK = new CrystalBlock("deep_crystal_block", Material.GLASS);
	public static final Block DEMON_CRYSTAL_BLOCK = new CrystalBlock("demon_crystal_block", Material.GLASS);
	public static final BlockSlab CLEAN_STONE_SLAB_DOUBLE = new CleanStoneDoubleSlab("clean_stone_slab_double", Material.ROCK);
	public static final BlockSlab CLEAN_STONE_SLAB_HALF = new CleanStoneHalfSlab("clean_stone_slab_half", Material.ROCK);
	public static final BlockStairs CLEAN_STONE_STAIRS = new StairBase("clean_stone_stairs", Blocks.STONE.getDefaultState());
	public static final Block DWARF_LAMP = new DwarfLamp("dwarf_lamp", Material.CIRCUITS);
	public static final Block RUNE = new Rune("rune", Material.CIRCUITS);
	public static final Block MAGIC_LIGHT = new MagicLight("magic_light", Material.SNOW);
	public static final Block DWARF_BASE_MARKER = new DwarfBaseMarker("dwarf_base_marker", Material.ROCK);

	//Insanity Blocks
	public static final Block INSANITY_STONE = new InsanityStone("insanity_stone", Material.ROCK);
	public static final Block INSANITY_COBBLE = new InsanityCobble("insanity_cobble", Material.ROCK);
	public static final Block INSANITY_DIRT = new InsanityDirt("insanity_dirt", Material.GROUND);
	public static final Block INSANITY_GRASS = new InsanityGrass("insanity_grass", Material.GRASS);
	public static final Block INSANITY_PLANKS = new InsanityPlanks("insanity_planks", Material.WOOD);
	public static final BlockBush INSANITY_FLOWER = new InsanityFlower("insanity_flower", Material.PLANTS);
	public static final BlockBush INSANITY_FLOWER2 = new InsanityFlower("insanity_flower2", Material.PLANTS);
	public static final BlockBush INSANITY_FLOWER3 = new InsanityFlower("insanity_flower3", Material.PLANTS);
	public static final BlockBush INSANITY_FLOWER4 = new InsanityFlower("insanity_flower4", Material.PLANTS);
	public static final BlockBush INSANITY_FLOWER5 = new InsanityFlower("insanity_flower5", Material.PLANTS);
	public static final BlockBush INSANITY_TALL_GRASS = new InsanityTallGrass("insanity_tall_grass", Material.VINE);
	public static final Block INSANITY_LOG = new InsanityLog("insanity_log");
	public static final Block INSANITY_LEAVES = new InsanityLeaves("insanity_leaves");
	public static final BlockBush INSANITY_SAPLING = new InsanitySapling("insanity_sapling", Material.PLANTS);
	public static final BlockSlab INSANITY_STONE_SLAB_DOUBLE = new InsanityStoneDoubleSlab("insanity_stone_slab_double", Material.ROCK);
	public static final BlockSlab INSANITY_STONE_SLAB_HALF = new InsanityStoneHalfSlab("insanity_stone_slab_half", Material.ROCK);
	public static final BlockStairs INSANITY_STONE_STAIRS = new StairBase("insanity_stone_stairs", INSANITY_STONE.getDefaultState());
	public static final BlockSlab INSANITY_COBBLE_SLAB_DOUBLE = new InsanityCobbleDoubleSlab("insanity_cobble_slab_double", Material.ROCK);
	public static final BlockSlab INSANITY_COBBLE_SLAB_HALF = new InsanityCobbleHalfSlab("insanity_cobble_slab_half", Material.ROCK);
	public static final BlockStairs INSANITY_COBBLE_STAIRS = new StairBase("insanity_cobble_stairs", INSANITY_COBBLE.getDefaultState());
	public static final BlockSlab INSANITY_WOOD_SLAB_DOUBLE = new InsanityWoodDoubleSlab("insanity_wood_slab_double", Material.WOOD);
	public static final BlockSlab INSANITY_WOOD_SLAB_HALF = new InsanityWoodHalfSlab("insanity_wood_slab_half", Material.WOOD);
	public static final BlockStairs INSANITY_WOOD_STAIRS = new StairBase("insanity_wood_stairs", INSANITY_PLANKS.getDefaultState());
	public static final Block INSANITY_WATER = new BlockFluid("insanity_water", ModFluids.INSANITY_WATER, Material.WATER);
	//Insanity Ores
	public static final Block INSANITY_COAL_ORE = new InsanityOreCoal("insanity_coal_ore", Material.ROCK);
	public static final Block INSANITY_IRON_ORE = new InsanityOreIron("insanity_iron_ore", Material.ROCK);
	public static final Block INSANITY_REDSTONE_ORE = new InsanityOreRedstone("insanity_redstone_ore", Material.ROCK);
	public static final Block INSANITY_LAPIS_ORE = new InsanityOreLapis("insanity_lapis_ore", Material.ROCK);
	public static final Block INSANITY_GOLD_ORE = new InsanityOreGold("insanity_gold_ore", Material.ROCK);
	public static final Block INSANITY_DIAMOND_ORE = new InsanityOreDiamond("insanity_diamond_ore", Material.ROCK);
	public static final Block INSANITY_EMERALD_ORE = new InsanityOreEmerald("insanity_emerald_ore", Material.ROCK);
	public static final Block INSANITY_DEEP_CRYSTAL_ORE = new DeepCrystalOre("insanity_deep_crystal_ore", Material.ROCK);

	//Depth Blocks
	public static final Block DEPTH_STONE = new DepthStone("depth_stone", Material.ROCK);
	public static final Block DEPTH_COBBLE = new DepthCobble("depth_cobble", Material.ROCK);
	public static final Block DEPTH_DIRT = new DepthDirt("depth_dirt", Material.GROUND);
	public static final Block DEPTH_PLANKS = new DepthPlanks("depth_planks", Material.WOOD);
	public static final BlockBush DEPTH_FLOWER = new DepthFlower("depth_flower", Material.PLANTS);
	public static final Block DEPTH_LOG = new DepthLog("depth_log");
	public static final Block DEPTH_LEAVES = new DepthLeaves("depth_leaves");
	public static final Block DEPTH_GLOWSTONE = new DepthGlowstone("depth_glowstone", Material.GLASS);
	public static final BlockBush DEPTH_SAPLING = new DepthSapling("depth_sapling", Material.PLANTS);
	public static final BlockSlab DEPTH_STONE_SLAB_DOUBLE = new DepthStoneDoubleSlab("depth_stone_slab_double", Material.WOOD);
	public static final BlockSlab DEPTH_STONE_SLAB_HALF = new DepthStoneHalfSlab("depth_stone_slab_half", Material.WOOD);
	public static final BlockStairs DEPTH_STONE_STAIRS = new StairBase("depth_stone_stairs", DEPTH_STONE.getDefaultState());
	public static final BlockSlab DEPTH_COBBLE_SLAB_DOUBLE = new DepthCobbleDoubleSlab("depth_cobble_slab_double", Material.WOOD);
	public static final BlockSlab DEPTH_COBBLE_SLAB_HALF = new DepthCobbleHalfSlab("depth_cobble_slab_half", Material.WOOD);
	public static final BlockStairs DEPTH_COBBLE_STAIRS = new StairBase("depth_cobble_stairs", DEPTH_COBBLE.getDefaultState());
	public static final BlockSlab DEPTH_WOOD_SLAB_DOUBLE = new DepthWoodDoubleSlab("depth_wood_slab_double", Material.WOOD);
	public static final BlockSlab DEPTH_WOOD_SLAB_HALF = new DepthWoodHalfSlab("depth_wood_slab_half", Material.WOOD);
	public static final BlockStairs DEPTH_WOOD_STAIRS = new StairBase("depth_wood_stairs", DEPTH_PLANKS.getDefaultState());
	//Depth Ores
	public static final Block DEPTH_COAL_ORE = new DepthOreCoal("depth_coal_ore", Material.ROCK);
	public static final Block DEPTH_IRON_ORE = new DepthOreIron("depth_iron_ore", Material.ROCK);
	public static final Block DEPTH_REDSTONE_ORE = new DepthOreRedstone("depth_redstone_ore", Material.ROCK);
	public static final Block DEPTH_LAPIS_ORE = new DepthOreLapis("depth_lapis_ore", Material.ROCK);
	public static final Block DEPTH_GOLD_ORE = new DepthOreGold("depth_gold_ore", Material.ROCK);
	public static final Block DEPTH_DIAMOND_ORE = new DepthOreDiamond("depth_diamond_ore", Material.ROCK);
	public static final Block DEPTH_EMERALD_ORE = new DepthOreEmerald("depth_emerald_ore", Material.ROCK);
	public static final Block DEEP_CRYSTAL_ORE_COMPRESSED = new DeepCrystalOreCompressed("deep_crystal_ore_compressed", Material.ROCK);

	//Dungeon Blocks
	public static final Block DUNGEON_STONE = new DungeonStone("dungeon_stone", Material.ROCK);
	public static final Block DUNGEON_COBBLE = new DungeonStone("dungeon_cobble", Material.ROCK);
	public static final Block DUNGEON_COBBLE_MOSSY = new DungeonStone("dungeon_cobble_mossy", Material.ROCK);
	public static final Block DUNGEON_BRICK = new DungeonStone("dungeon_brick", Material.ROCK);
	public static final Block DUNGEON_BRICK_MOSSY = new DungeonStone("dungeon_brick_mossy", Material.ROCK);
	public static final Block DUNGEON_BRICK_CRACKED = new DungeonStone("dungeon_brick_cracked", Material.ROCK);

	//Special Blocks
	public static final Block DEFENCE_STONE = new DefenceStone("defence_stone", Material.ROCK);
	public static final Block ATTACK_STONE = new AttackStone("attack_stone", Material.ROCK);
	public static final Block SPEED_STONE = new PotionEffectStone("speed_stone", Material.ROCK, MobEffects.SPEED, 3, 3, true);
	public static final Block HEAL_STONE = new PotionEffectStone("heal_stone", Material.ROCK, MobEffects.REGENERATION, 2, 3);
	public static final Block VISION_STONE = new PotionEffectStone("vision_stone", Material.ROCK, MobEffects.NIGHT_VISION, 11, 1, true);
	public static final Block SATURATION_STONE = new PotionEffectStone("saturation_stone", Material.ROCK, MobEffects.SATURATION, 60, 3, true);
	public static final Block LEVITATION_STONE = new LevitationStone("levitation_stone", Material.ROCK);
	public static final Block POISON_STONE = new PotionEffectStone("poison_stone", Material.ROCK, MobEffects.POISON, 5, 1);
	public static final Block DEMON_STONE = new DemonStone("demon_stone", Material.ROCK);

	//Interacting Blocks
	public static final SpellStone SPELL_STONE = new SpellStone("spell_stone", Material.ROCK);
	public static final Block ALTAR_OF_KNOWLEDGE = new AltarOfKnowledge("altar_of_knowledge", Material.ROCK);

	//Ritual Blocks
	public static final Block RITUAL_BASE = new RitualStructure("ritual_base", Material.ROCK);//TODO: Add recipe
	public static final Block RITUAL_TIME = new RitualTime("ritual_time", Material.ROCK);//TODO: Add recipe
	public static final Block RITUAL_WEATHER = new RitualWeather("ritual_weather", Material.ROCK);//TODO: Add recipe
	public static final Block RITUAL_SPAWN_DENIAL = new RitualSpawnDenial("ritual_spawn_denial", Material.ROCK);//TODO: Add recipe
	public static final Block RITUAL_QUARRY = new RitualQuarry("ritual_quarry", Material.ROCK);//TODO: Add recipe

	//Mana System Blocks
	public static final ManaAltar MANA_ALTAR = new ManaAltar("mana_altar", Material.ROCK);
	public static final ManaRelay MANA_RELAY = new ManaRelay("mana_relay", Material.ROCK);
	public static final ManaGeneratorNormal MANA_GENERATOR_NORMAL = new ManaGeneratorNormal("mana_generator_normal", Material.ROCK);
	public static final ManaGeneratorInsanity MANA_GENERATOR_INSANITY = new ManaGeneratorInsanity("mana_generator_insanity", Material.ROCK);
	public static final ManaGeneratorMob MANA_GENERATOR_MOB = new ManaGeneratorMob("mana_generator_mob", Material.ROCK);
	public static final ManaInfuser MANA_INFUSER = new ManaInfuser("mana_infuser", Material.ROCK);
	public static final ManaGrinder MANA_GRINDER = new ManaGrinder("mana_grinder", Material.ROCK);
	public static final ManaFurnace MANA_FURNACE = new ManaFurnace("mana_furnace", Material.ROCK);
}