package com.fi0x.deepmagic.init;

import java.util.ArrayList;
import java.util.List;

import com.fi0x.deepmagic.items.ArmorBase;
import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.items.food.FoodEffectBase;
import com.fi0x.deepmagic.items.mana.ManaBooster;
import com.fi0x.deepmagic.items.mana.ManaMonitor;
import com.fi0x.deepmagic.items.mana.ManaWaster;
import com.fi0x.deepmagic.items.mana.TeleportationCrystal;
import com.fi0x.deepmagic.items.tools.ToolAxe;
import com.fi0x.deepmagic.items.tools.ToolHoe;
import com.fi0x.deepmagic.items.tools.ToolPickaxe;
import com.fi0x.deepmagic.items.tools.ToolSpade;
import com.fi0x.deepmagic.items.tools.ToolSword;
import com.fi0x.deepmagic.util.Reference;

import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems
{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
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
	public static final Item MANA_MONITOR = new ManaMonitor("mana_monitor");
	
	//Tools
	public static final ItemSword DEEP_CRYSTAL_SWORD = new ToolSword("deep_crystal_sword", MATERIAL_DEEP_CRYSTAL);
	public static final ItemSpade DEEP_CRYSTAL_SHOVEL = new ToolSpade("deep_crystal_shovel", MATERIAL_DEEP_CRYSTAL);
	public static final ItemPickaxe DEEP_CRYSTAL_PICKAXE = new ToolPickaxe("deep_crystal_pickaxe", MATERIAL_DEEP_CRYSTAL);
	public static final ItemAxe DEEP_CRYSTAL_AXE = new ToolAxe("deep_crystal_axe", MATERIAL_DEEP_CRYSTAL);
	public static final ItemHoe DEEP_CRYSTAL_HOE = new ToolHoe("deep_crystal_hoe", MATERIAL_DEEP_CRYSTAL);
	
	//Armor
	public static final Item DEEP_CRYSTAL_HELMET = new ArmorBase("deep_crystal_helmet", ARMOR_MATERIAL_DEEP_CRYSTAL, 1, EntityEquipmentSlot.HEAD);
	public static final Item DEEP_CRYSTAL_CHESTPLATE = new ArmorBase("deep_crystal_chestplate", ARMOR_MATERIAL_DEEP_CRYSTAL, 1, EntityEquipmentSlot.CHEST);
	public static final Item DEEP_CRYSTAL_LEGGINGS = new ArmorBase("deep_crystal_leggings", ARMOR_MATERIAL_DEEP_CRYSTAL, 2, EntityEquipmentSlot.LEGS);
	public static final Item DEEP_CRYSTAL_BOOTS = new ArmorBase("deep_crystal_boots", ARMOR_MATERIAL_DEEP_CRYSTAL, 1, EntityEquipmentSlot.FEET);
	
	//Food
	public static final Item CRYSTAL_INFUSED_APPLE = new FoodEffectBase("crystal_infused_apple", 2, 1.0F, false, new PotionEffect(MobEffects.STRENGTH, (20*20), 1, false, true));
}