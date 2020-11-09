package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.items.ArmorBase;
import com.fi0x.deepmagic.items.DemonCrystal;
import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.items.food.FoodBase;
import com.fi0x.deepmagic.items.food.FoodEffectBase;
import com.fi0x.deepmagic.items.mana.*;
import com.fi0x.deepmagic.items.tools.*;
import com.fi0x.deepmagic.util.Reference;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

public class ModItems
{
	public static final List<Item> ITEMS = new ArrayList<>();
	
	//Materials
	public static final ToolMaterial MATERIAL_DEEP_CRYSTAL = EnumHelper.addToolMaterial("material_deep_crystal", 4, 1561, 10.0F, 4.0F, 10);
	public static final ArmorMaterial ARMOR_MATERIAL_DEEP_CRYSTAL = EnumHelper.addArmorMaterial("armor_material_deep_crystal", Reference.MOD_ID + ":deep_crystal", 16, new int[] {4, 9, 7, 4}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F);
	
	//Items
	public static final Item TELEPORTATION_CRYSTAL = new TeleportationCrystal("teleportation_crystal");
	public static final Item MANA_BOOSTER = new ManaBooster("mana_booster");
	public static final Item DEMON_CRYSTAL = new DemonCrystal("demon_crystal");
	public static final Item SKILLPOINT = new Skillpoint("skillpoint");
	public static final Item MANA_LINKER = new ManaLinker("mana_linker");
	
	//Tools
	public static final ItemSword DEEP_CRYSTAL_SWORD = new ToolSword("deep_crystal_sword", MATERIAL_DEEP_CRYSTAL);
	public static final ItemSpade DEEP_CRYSTAL_SHOVEL = new ToolSpade("deep_crystal_shovel", MATERIAL_DEEP_CRYSTAL);
	public static final ItemPickaxe DEEP_CRYSTAL_PICKAXE = new ToolPickaxe("deep_crystal_pickaxe", MATERIAL_DEEP_CRYSTAL);
	public static final ItemAxe DEEP_CRYSTAL_AXE = new ToolAxe("deep_crystal_axe", MATERIAL_DEEP_CRYSTAL);
	public static final ItemHoe DEEP_CRYSTAL_HOE = new ToolHoe("deep_crystal_hoe", MATERIAL_DEEP_CRYSTAL);

	//Spells
	public static final Item SPELL = new Spell("spell");
	public static final Item CHARGED_SPELL = new ManaChargedSpell("charged_spell");
	public static final Item BREAKABLE_SPELL = new BreakableSpell("breakable_spell");
	public static final Item SPELL_COMPONENT = new SpellComponent("spell_component");
	
	//Armor
	public static final Item DEEP_CRYSTAL_HELMET = new ArmorBase("deep_crystal_helmet", ARMOR_MATERIAL_DEEP_CRYSTAL, 1, EntityEquipmentSlot.HEAD);
	public static final Item DEEP_CRYSTAL_CHESTPLATE = new ArmorBase("deep_crystal_chestplate", ARMOR_MATERIAL_DEEP_CRYSTAL, 1, EntityEquipmentSlot.CHEST);
	public static final Item DEEP_CRYSTAL_LEGGINGS = new ArmorBase("deep_crystal_leggings", ARMOR_MATERIAL_DEEP_CRYSTAL, 2, EntityEquipmentSlot.LEGS);
	public static final Item DEEP_CRYSTAL_BOOTS = new ArmorBase("deep_crystal_boots", ARMOR_MATERIAL_DEEP_CRYSTAL, 1, EntityEquipmentSlot.FEET);
	
	//Food
	public static final Item CRYSTAL_INFUSED_APPLE = new FoodEffectBase("crystal_infused_apple", 2, 1.0F, false, new PotionEffect(MobEffects.STRENGTH, (20*20), 1, false, true), true);
	public static final Item INSANITY_APPLE = new FoodEffectBase("insanity_apple", 4, 1F, false, new PotionEffect(MobEffects.NAUSEA, 10*20, 1, false, false), false);
	public static final Item RAW_COCKROACH = new FoodEffectBase("raw_cockroach", 2, 4, false, new PotionEffect(MobEffects.POISON, 5*20, 1, false, false), false);
	public static final Item COOKED_COCKROACH = new FoodBase("cooked_cockroach", 4, 4, false);
	public static final Item COCKROACH_SNACK = new FoodBase("cockroach_snack", 6, 8, false);
	public static final Item RAW_WORM = new FoodEffectBase("raw_worm", 2, 4, false, new PotionEffect(MobEffects.POISON, 5*20, 1, false, false), false);
	public static final Item COOKED_WORM = new FoodBase("cooked_worm", 4, 4, false);
	public static final Item WORM_SNACK = new FoodBase("worm_snack", 6, 8, false);
	public static final Item DRY_FLESH = new FoodBase("dry_flesh", 4, 1, false);
	public static final Item WHEAT_FLOUR = new ItemBase("wheat_flour");

	//Crafting Items
	public static final Item DEEP_CRYSTAL = new ItemBase("deep_crystal");
	public static final Item COMPRESSED_DEEP_CRYSTAL = new ItemBase("compressed_deep_crystal");
	public static final Item DEEP_CRYSTAL_POWDER = new ItemBase("deep_crystal_powder");
	public static final Item MANA_BATTERY = new ItemBase("mana_battery");
	public static final Item MAGIC_CONVERTER = new ItemBase("magic_converter");
	public static final Item EMPTY_SCROLL = new ItemBase("empty_scroll");
	public static final Item DIMENSIONAL_CRYSTAL = new ItemBase("dimensional_crystal");
	public static final Item MAGIC_POWDER = new ItemBase("magic_powder");
	public static final Item MAGIC_SIGIL = new ItemBase("magic_sigil");
	public static final Item MAGIC_FLOW_CONTROLLER = new ItemBase("magic_flow_controller");
	public static final Item MANA_INTERFACE = new ItemBase("mana_interface");
	public static final Item KNOWLEDGE_SEGMENT = new ItemBase("knowledge_segment");
	public static final Item WORLD_INTERACTION_SEGMENT = new ItemBase("world_interaction_segment");
	public static final Item MANA_SEGMENT = new ItemBase("mana_segment");
}