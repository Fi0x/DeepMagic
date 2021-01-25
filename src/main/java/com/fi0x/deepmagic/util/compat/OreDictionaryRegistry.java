package com.fi0x.deepmagic.util.compat;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryRegistry
{
    public static final int WILDCARD_VALUE = Short.MAX_VALUE;

    public static void registerOres()
    {
        //Minecraft
        OreDictionary.registerOre("apple", new ItemStack(Items.APPLE, 1, WILDCARD_VALUE));

        //Mod Items
        OreDictionary.registerOre("apple", new ItemStack(ModItems.INSANITY_APPLE, 1, WILDCARD_VALUE));

        //Mod Blocks
        OreDictionary.registerOre("torch", new ItemStack(ModBlocks.DWARF_LAMP, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("torch", new ItemStack(ModBlocks.MAGIC_LIGHT, 1, WILDCARD_VALUE));

        //Insanity Blocks
        OreDictionary.registerOre("stone", new ItemStack(ModBlocks.INSANITY_STONE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("cobblestone", new ItemStack(ModBlocks.INSANITY_COBBLE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("dirt", new ItemStack(ModBlocks.INSANITY_DIRT, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("grass", new ItemStack(ModBlocks.INSANITY_GRASS, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("plankWood", new ItemStack(ModBlocks.INSANITY_PLANKS, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.INSANITY_LOG, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("treeLeaves", new ItemStack(ModBlocks.INSANITY_LEAVES, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("treeSapling", new ItemStack(ModBlocks.INSANITY_SAPLING, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("slabWood", new ItemStack(ModBlocks.INSANITY_WOOD_SLAB_HALF, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("stairWood", new ItemStack(ModBlocks.INSANITY_WOOD_STAIRS, 1, WILDCARD_VALUE));
        //Insanity Ores
        OreDictionary.registerOre("oreCoal", new ItemStack(ModBlocks.INSANITY_COAL_ORE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("oreIron", new ItemStack(ModBlocks.INSANITY_IRON_ORE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("oreRedstone", new ItemStack(ModBlocks.INSANITY_REDSTONE_ORE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("oreLapis", new ItemStack(ModBlocks.INSANITY_LAPIS_ORE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("oreGold", new ItemStack(ModBlocks.INSANITY_GOLD_ORE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("oreDiamond", new ItemStack(ModBlocks.INSANITY_DIAMOND_ORE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("oreEmerald", new ItemStack(ModBlocks.INSANITY_EMERALD_ORE, 1, WILDCARD_VALUE));

        //Depth Blocks
        OreDictionary.registerOre("stone", new ItemStack(ModBlocks.DEPTH_STONE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("cobblestone", new ItemStack(ModBlocks.DEPTH_COBBLE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("dirt", new ItemStack(ModBlocks.DEPTH_DIRT, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("plankWood", new ItemStack(ModBlocks.DEPTH_PLANKS, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.DEPTH_LOG, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("glowstone", new ItemStack(ModBlocks.DEPTH_GLOWSTONE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("slabWood", new ItemStack(ModBlocks.DEPTH_WOOD_SLAB_HALF, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("stairWood", new ItemStack(ModBlocks.DEPTH_WOOD_STAIRS, 1, WILDCARD_VALUE));
        //Depth Ores
        OreDictionary.registerOre("oreCoal", new ItemStack(ModBlocks.DEPTH_COAL_ORE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("oreIron", new ItemStack(ModBlocks.DEPTH_IRON_ORE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("oreRedstone", new ItemStack(ModBlocks.DEPTH_REDSTONE_ORE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("oreLapis", new ItemStack(ModBlocks.DEPTH_LAPIS_ORE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("oreGold", new ItemStack(ModBlocks.DEPTH_GOLD_ORE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("oreDiamond", new ItemStack(ModBlocks.DEPTH_DIAMOND_ORE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("oreEmerald", new ItemStack(ModBlocks.DEPTH_EMERALD_ORE, 1, WILDCARD_VALUE));

        //Custom Ore Dictionary Entries
        OreDictionary.registerOre("oreDeepCrystal", new ItemStack(ModBlocks.DEEP_CRYSTAL_ORE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("oreDeepCrystal", new ItemStack(ModBlocks.DEEP_CRYSTAL_NETHER_ORE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("oreDeepCrystal", new ItemStack(ModBlocks.DEEP_CRYSTAL_END_ORE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("oreDeepCrystal", new ItemStack(ModBlocks.INSANITY_DEEP_CRYSTAL_ORE, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("oreDeepCrystal", new ItemStack(ModBlocks.DEEP_CRYSTAL_ORE_COMPRESSED, 1, WILDCARD_VALUE));
    }
}
