package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.items.ArmorBase;
import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.items.food.FoodBase;
import com.fi0x.deepmagic.items.food.FoodEffectBase;
import com.fi0x.deepmagic.items.mana.ManaBooster;
import com.fi0x.deepmagic.items.mana.ManaWaster;
import com.fi0x.deepmagic.items.mana.TeleportationCrystal;
import com.fi0x.deepmagic.items.spells.*;
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
	public static final Item DEEP_CRYSTAL = new ItemBase("deep_crystal");
	public static final Item COMPRESSED_DEEP_CRYSTAL = new ItemBase("compressed_deep_crystal");
	public static final Item DEEP_CRYSTAL_POWDER = new ItemBase("deep_crystal_powder");
	public static final Item TELEPORTATION_CRYSTAL = new TeleportationCrystal("teleportation_crystal");
	public static final Item MANA_BOOSTER = new ManaBooster("mana_booster");
	public static final Item MANA_WASTER = new ManaWaster("mana_waster");
	
	//Tools
	public static final ItemSword DEEP_CRYSTAL_SWORD = new ToolSword("deep_crystal_sword", MATERIAL_DEEP_CRYSTAL);
	public static final ItemSpade DEEP_CRYSTAL_SHOVEL = new ToolSpade("deep_crystal_shovel", MATERIAL_DEEP_CRYSTAL);
	public static final ItemPickaxe DEEP_CRYSTAL_PICKAXE = new ToolPickaxe("deep_crystal_pickaxe", MATERIAL_DEEP_CRYSTAL);
	public static final ItemAxe DEEP_CRYSTAL_AXE = new ToolAxe("deep_crystal_axe", MATERIAL_DEEP_CRYSTAL);
	public static final ItemHoe DEEP_CRYSTAL_HOE = new ToolHoe("deep_crystal_hoe", MATERIAL_DEEP_CRYSTAL);

	//Spells
	public static final Item SPELL_HEAL_T1 = new SpellHeal("spell_heal_t1", 1);
	public static final Item SPELL_HEAL_T2 = new SpellHeal("spell_heal_t2", 2);
	public static final Item SPELL_HEAL_T3 = new SpellHeal("spell_heal_t3", 3);
	public static final Item SPELL_HEAL_T4 = new SpellHeal("spell_heal_t4", 4);
	public static final Item SPELL_HEAL_T5 = new SpellHeal("spell_heal_t5", 5);

	public static final Item SPELL_TIME_DAY = new SpellTime("spell_time_day", 5000);
	public static final Item SPELL_TIME_NIGHT = new SpellTime("spell_time_night", 17000);

	public static final Item SPELL_WEATHER_T1 = new SpellWeather("spell_weather_t1", 1);
	public static final Item SPELL_WEATHER_T2 = new SpellWeather("spell_weather_t2", 2);
	public static final Item SPELL_WEATHER_T3 = new SpellWeather("spell_weather_t3", 3);

	public static final Item SPELL_MOB_ANNIHILATION_T8 = new SpellMobAnnihilation("spell_mob_annihilation_t8", 8);
	public static final Item SPELL_MOB_ANNIHILATION_T9 = new SpellMobAnnihilation("spell_mob_annihilation_t9", 9);
	public static final Item SPELL_MOB_ANNIHILATION_T10 = new SpellMobAnnihilation("spell_mob_annihilation_t10", 10);

	public static final Item SPELL_RANGED_MOB_ANNIHILATION_T8 = new SpellRangedMobAnnihilation("spell_ranged_mob_annihilation_t8", 8);
	public static final Item SPELL_RANGED_MOB_ANNIHILATION_T9 = new SpellRangedMobAnnihilation("spell_ranged_mob_annihilation_t9", 9);
	public static final Item SPELL_RANGED_MOB_ANNIHILATION_T10 = new SpellRangedMobAnnihilation("spell_ranged_mob_annihilation_t10", 10);
	
	//Armor
	public static final Item DEEP_CRYSTAL_HELMET = new ArmorBase("deep_crystal_helmet", ARMOR_MATERIAL_DEEP_CRYSTAL, 1, EntityEquipmentSlot.HEAD);
	public static final Item DEEP_CRYSTAL_CHESTPLATE = new ArmorBase("deep_crystal_chestplate", ARMOR_MATERIAL_DEEP_CRYSTAL, 1, EntityEquipmentSlot.CHEST);
	public static final Item DEEP_CRYSTAL_LEGGINGS = new ArmorBase("deep_crystal_leggings", ARMOR_MATERIAL_DEEP_CRYSTAL, 2, EntityEquipmentSlot.LEGS);
	public static final Item DEEP_CRYSTAL_BOOTS = new ArmorBase("deep_crystal_boots", ARMOR_MATERIAL_DEEP_CRYSTAL, 1, EntityEquipmentSlot.FEET);
	
	//Food
	public static final Item CRYSTAL_INFUSED_APPLE = new FoodEffectBase("crystal_infused_apple", 2, 1.0F, false, new PotionEffect(MobEffects.STRENGTH, (20*20), 1, false, true));
	public static final Item INSANITY_APPLE = new FoodEffectBase("insanity_apple", 4, 1F, false, new PotionEffect(MobEffects.NAUSEA, 10*20, 1, false, false));
	public static final Item STONE_SNACK = new FoodBase("stone_snack", 5, 10, false);
	public static final Item RAW_COCKROACH = new FoodBase("raw_cockroach", 2, 4, false);
	public static final Item COOKED_COCKROACH = new FoodBase("cooked_cockroach", 4, 4, false);
	public static final Item COCKROACH_SNACK = new FoodBase("cockroach_snack", 6, 8, false);
}