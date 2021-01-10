package com.fi0x.deepmagic.util.handlers;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.util.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class ConfigHandler
{
    public static Configuration ids;
    public static Configuration worldGeneration;
    public static Configuration npcsGeneral;
    public static Configuration npcStats;
    public static Configuration player;
    public static Configuration blocks;
    public static Configuration items;

    //Dimension IDs
    public static int dimensionIdInsanityID;
    //Entity IDs
    public static int entityInsanityCowID;
    public static int entityDwarfID;
    public static int entityHoveringOrbID;
    public static int entityNetherWormID;
    public static int entityGiantID;
    public static int entityRockTrollID;
    public static int entityDepthMageID;
    public static int entityDemonID;
    public static int entityCockroachID;
    public static int entityWormID;
    public static int entityCyclopesID;
    //Gui IDs
    public static int guiManaAltarID;
    public static int guiManaGeneratorNormalID;
    public static int guiManaGeneratorInsanityID;
    public static int guiManaGeneratorMobID;
    public static int guiManaInfuserID;
    public static int guiManaGrinderID;
    public static int guiManaFurnaceID;
    //Particle IDs
    public static int firstParticleID;

    //Biome Generation
    public static boolean overworldInsanityBiome;
    public static int insanityBiomeWeight;
    //Ore Generation
    public static boolean spawnDeepCrystalOre;
    //Structure Generation
    public static boolean generateMageHouses;
    public static boolean generateInsanityRockTrollCaves;
    public static boolean generateShrines;
    public static boolean generateInsanityOases;
    public static boolean generateDwarfBases;
    public static boolean generateDragonLairs;
    public static boolean generateDungeons;

    //Insanity Biome SpawnList
    public static boolean allowCockroach;
    public static boolean allowInsanityCow;
    public static boolean allowDepthMage;
    public static boolean allowHoveringOrb;
    public static boolean allowGiant;
    //NPC Behavior
    public static boolean dwarfMining;
    public static boolean trollDefenceState;
    public static int dwarfMaxMiningHeight;
    //Whitelist
    public static boolean dwarfMineOres;
    public static boolean dwarfMineResources;

    //NPC Health
    public static int healthCockroach;
    public static int healthCyclopse;
    public static int healthDemon;
    public static int healthDepthMage;
    public static int healthDwarf;
    public static int healthGiant;
    public static int healthHoveringOrb;
    public static int healthInsanityCow;
    public static int healthNetherWorm;
    public static int healthRockTroll;
    public static int healthWorm;
    //NPC Damage
    public static int damageCyclopse;
    public static int damageDemon;
    public static int damageDepthMage;
    public static int damageDwarf;
    public static int damageGiant;
    public static int damageHoveringOrb;
    public static int damageNetherWorm;
    public static int damageRockTroll;
    //NPC Utility
    public static int aiSearchRange;
    public static int dwarfMineRange;
    public static int dwarfInventorySlots;
    public static boolean showAISearchParticles;

    //Player Mana
    public static int baseMana;
    public static int manaXPForLevelup;
    //Player Visuals
    public static boolean manaOverlayAlwaysVisible;
    public static boolean showSkillpointAddedText;

    //Mana Amounts
    public static int manaAltarCapacity;
    public static int manaGeneratorManaCapacity;
    public static int manaMachineManaCapacity;
    public static int manaGainFromMob;
    //Block Ranges
    public static int manaBlockTransferRange;
    public static int manaGeneratorMobRange;
    //Insanity Water
    public static boolean insanityWaterEffect;
    //Demon Stone
    public static int demonSummonCost;
    public static int demonSummonXP;
    public static boolean requireDemonStructure;
    //Magic Light
    public static int magicLightParticles;
    //Dwarf Base Marker
    public static int dwarfBaseMarkerParticles;
    public static int dwarfMarkerSpawnChance;

    //Mana Costs
    public static int manaBoosterAmount;
    public static int teleportationCrystalManaCost;
    public static int spellBaseManaCost;
    //Skill XP
    public static int teleportationCrystalSkillXP;

    public static void registerConfig(FMLPreInitializationEvent event)
    {
        Main.config = new File(event.getModConfigurationDirectory() + "/" + Reference.MOD_ID);
        Main.config.mkdirs();
        initIDs(new File(Main.config.getPath(), "ModIDs.cfg"));
        initWorldGen(new File(Main.config.getPath(), "ModWorldGen.cfg"));
        initNPCs(new File(Main.config.getPath(), "ModNPCs.cfg"));
        initNPCStats(new File(Main.config.getPath(), "ModNPCStats.cfg"));
        initPlayer(new File(Main.config.getPath(), "Player.cfg"));
        initBlocks(new File(Main.config.getPath(), "Blocks.cfg"));
        initItems(new File(Main.config.getPath(), "Items.cfg"));
    }

    public static void initIDs(File file)
    {
        ids = new Configuration(file);
        String category;

        category = "Mod Dimensions";
        ids.addCustomCategoryComment(category, "Mod Dimensions");
        dimensionIdInsanityID = ids.getInt("Insanity Dimension ID", category, 42, -1000, -1000, "");

        category = "Mod NPCs";
        ids.addCustomCategoryComment(category, "Mod NPCs");
        entityInsanityCowID = ids.getInt("Insanity Cow ID", category, 1764, -10000, 10000, "");
        entityDwarfID = ids.getInt("Dwarf ID", category, 1765, -10000, 10000, "");
        entityHoveringOrbID = ids.getInt("Hovering Orb ID", category, 1766, -10000, 10000, "");
        entityNetherWormID = ids.getInt("Nether Worm ID", category, 1767, -10000, 10000, "");
        entityGiantID = ids.getInt("Giant ID", category, 1768, -10000, 10000, "");
        entityRockTrollID = ids.getInt("Rock Troll ID", category, 1769, -10000, 10000, "");
        entityDepthMageID = ids.getInt("Depth Mage ID", category, 1770, -10000, 10000, "");
        entityDemonID = ids.getInt("Demon ID", category, 1771, -10000, 10000, "");
        entityCockroachID = ids.getInt("Cockroach ID", category, 1772, -10000, 10000, "");
        entityWormID = ids.getInt("Worm ID", category, 1773, -10000, 10000, "");
        entityCyclopesID = ids.getInt("Cyclopse ID", category, 1774, -10000, 10000, "");

        category = "GUIs";
        ids.addCustomCategoryComment(category, "GUIs");
        guiManaAltarID = ids.getInt("Mana Altar GUI ID", category, 420, -1000, 1000, "");
        guiManaGeneratorNormalID = ids.getInt("Normal Mana Generator GUI ID", category, 421, -1000, 1000, "");
        guiManaGeneratorInsanityID = ids.getInt("Insanity Mana Generator GUI ID", category, 422, -1000, 1000, "");
        guiManaGeneratorMobID = ids.getInt("Mob Mana Generator GUI ID", category, 423, -1000, 1000, "");
        guiManaInfuserID = ids.getInt("Mana Infuser GUI ID", category, 424, -1000, 1000, "");
        guiManaGrinderID = ids.getInt("Mana Grinder GUI ID", category, 425, -1000, 1000, "");
        guiManaFurnaceID = ids.getInt("Mana Furnace GUI ID", category, 426, -1000, 1000, "");

        category = "Particles";
        ids.addCustomCategoryComment(category, "Particles");
        firstParticleID = ids.getInt("The first ID Particles should use", category, 42, 42, 100000, "");

        ids.save();
    }
    public static void initWorldGen(File file)
    {
        worldGeneration = new Configuration(file);
        String category;

        category = "Biome Generation";
        worldGeneration.addCustomCategoryComment(category, "Biome Generation");
        overworldInsanityBiome = worldGeneration.getBoolean("Overworld Insanity Biome Spawn", category, false, "Enables Spawn of the Insanity Biome in the Overworld");
        insanityBiomeWeight = worldGeneration.getInt("Insanity Biome Weight", category, 10, 1, 1000, "Insanity Biome Spawn Weight");

        category = "Ore Generation";
        worldGeneration.addCustomCategoryComment(category, "Enable Ore Generation");
        spawnDeepCrystalOre = worldGeneration.getBoolean("Deep Crystal Ore", category, true, "Enables Deep Crystal Ore Generation in Default Dimensions");

        category = "Structure Generation";
        worldGeneration.addCustomCategoryComment(category, "Structure Generation");
        generateMageHouses = worldGeneration.getBoolean("Mage Houses", category, true, "Enables Mage Houses in the Insanity Dimension");
        generateInsanityRockTrollCaves = worldGeneration.getBoolean("Insanity Rock Troll Caves", category, true, "Enables Rock Troll Caves in the Insanity Dimension");
        generateShrines = worldGeneration.getBoolean("Shrines", category, true, "Enables Shrines in the Insanity Dimension");
        generateInsanityOases = worldGeneration.getBoolean("Insanity Oases", category, true, "Enables Oases in the Insanity Dimension");
        generateDwarfBases = worldGeneration.getBoolean("Dwarf Bases", category, true, "Enables Dwarf Bases in the Insanity Dimension");
        generateDragonLairs = worldGeneration.getBoolean("Dragon Lairs", category, true, "Enables Dragon Lairs in the Insanity Dimension");
        generateDungeons = worldGeneration.getBoolean("Dungeons", category, true, "Enables Dungeons in the Insanity Dimension");

        worldGeneration.save();
    }
    public static void initNPCs(File file)
    {
        npcsGeneral = new Configuration(file);
        String category;

        category = "Insanity Biome Spawns";
        npcsGeneral.addCustomCategoryComment(category, "Insanity Biome Spawns");
        allowCockroach = npcsGeneral.getBoolean("Allow Cockroach", category, true, "");
        allowInsanityCow = npcsGeneral.getBoolean("Allow Insanity Cow", category, true, "");
        allowDepthMage = npcsGeneral.getBoolean("Allow Depth Mage", category, true, "");
        allowHoveringOrb = npcsGeneral.getBoolean("Allow Hovering Orb", category, true, "");
        allowGiant = npcsGeneral.getBoolean("Allow Giant", category, true, "");

        category = "NPC Behavior";
        npcsGeneral.addCustomCategoryComment(category, "NPC Behavior");
        dwarfMining = npcsGeneral.getBoolean("Dwarf Mining", category, true, "Allow Dwarfs to dig tunnels");
        trollDefenceState = npcsGeneral.getBoolean("Troll Defence State", category, true, "Allow Trolls to use an invulnerable Defence State");
        dwarfMaxMiningHeight = npcsGeneral.getInt("Max Dwarf Mining Height", category, 50, 20, 250, "Maximum Height in which dwarfs dig mines");

        category = "NPC Whitelist";
        npcsGeneral.addCustomCategoryComment(category, "NPC Whitelist");
        dwarfMineOres = npcsGeneral.getBoolean("Dwarf Mine Ores", category, true, "Allow Dwarfs to mine Ores");
        dwarfMineResources = npcsGeneral.getBoolean("Dwarf Mine Resources", category, true, "Allow Dwarfs to mine Resource Blocks");

        npcsGeneral.save();
    }
    public static void initNPCStats(File file)
    {
        npcStats = new Configuration(file);
        String category;

        category = "Health";
        npcStats.addCustomCategoryComment(category, "NPC Health");
        healthCockroach = npcStats.getInt("Cockroach Max HP", category, 8, 1, 1000, "");
        healthCyclopse = npcStats.getInt("Cyclopse Max HP", category, 100, 1, 1000, "");
        healthDemon = npcStats.getInt("Demon Max HP", category, 100, 1, 1000, "");
        healthDepthMage = npcStats.getInt("Depth Mage Max HP", category, 40, 1, 1000, "");
        healthDwarf = npcStats.getInt("Dwarf Max HP", category, 16, 1, 1000, "");
        healthGiant = npcStats.getInt("Giant Max HP", category, 100, 1, 1000, "");
        healthHoveringOrb = npcStats.getInt("Hovering Orb Max HP", category, 16, 1, 1000, "");
        healthInsanityCow = npcStats.getInt("Insanity Cow Max HP", category, 30, 1, 1000, "");
        healthNetherWorm = npcStats.getInt("Nether Worm Max HP", category, 100, 1, 1000, "");
        healthRockTroll = npcStats.getInt("Rock Troll Max HP", category, 100, 1, 1000, "");
        healthWorm = npcStats.getInt("Worm Max HP", category, 5, 1, 1000, "");

        category = "Damage";
        npcStats.addCustomCategoryComment(category, "NPC Attack Damage");
        damageCyclopse = npcStats.getInt("Cyclopse Attack Damage", category, 7, 1, 1000, "");
        damageDemon = npcStats.getInt("Demon Attack Damage", category, 6, 1, 1000, "");
        damageDepthMage = npcStats.getInt("Depth Mage Attack Damage", category, 6, 1, 1000, "");
        damageDwarf = npcStats.getInt("Dwarf Attack Damage", category, 8, 1, 1000, "");
        damageGiant = npcStats.getInt("Giant Attack Damage", category, 5, 1, 1000, "");
        damageHoveringOrb = npcStats.getInt("Hovering Orb Attack Damage", category, 1, 1, 1000, "");
        damageNetherWorm = npcStats.getInt("Nether Worm Attack Damage", category, 10, 1, 1000, "");
        damageRockTroll = npcStats.getInt("Rock Troll Attack Damage", category, 8, 1, 1000, "");

        category = "Utility";
        npcStats.addCustomCategoryComment(category, "NPC Utility Stats");
        aiSearchRange = npcStats.getInt("AI Search Range", category, 32, 8, 256, "The Radius in which AIs search for Things");
        dwarfMineRange = npcStats.getInt("Dwarf Mine Range", category, 64, 16, 128, "How far away of a Dwarf Base Marker a Dwarf can mine");
        dwarfInventorySlots = npcStats.getInt("Dwarf Inventory Size", category, 3, 1, 30, "Amount of Stacks a Dwarf can carry");
        showAISearchParticles = npcStats.getBoolean("AI Search Particles", category, false, "Visualizes the current action of AIs");

        npcStats.save();
    }
    public static void initPlayer(File file)
    {
        player = new Configuration(file);
        String category;

        category = "Mana";
        player.addCustomCategoryComment(category, "Mana");
        baseMana = player.getInt("Base Mana", category, 100, 1, 10000, "The Mana Capacity a new Player has");
        manaXPForLevelup = player.getInt("XP/Skillpoint", category, 100, 1, 10000, "The XP amount needed to get a Skillpoint");

        category = "Visuals";
        player.addCustomCategoryComment(category, "Visuals");
        manaOverlayAlwaysVisible = player.getBoolean("Mana Overlay Always Visible", category, false, "Mana Bar is always visible, not only when a magic item is held");
        showSkillpointAddedText = player.getBoolean("Show Skillpoint added Text", category, true, "Enables the Message a player gets when gaining a Skillpoint");

        player.save();
    }
    public static void initBlocks(File file)
    {
        blocks = new Configuration(file);
        String category;

        category = "Mana Amounts";
        blocks.addCustomCategoryComment(category, "Mana Amounts");
        manaAltarCapacity = blocks.getInt("Mana Altar Capacity", category, 10000, 1, 100000, "The Base Capacity of the Mana Altar");
        manaGeneratorManaCapacity = blocks.getInt("Mana Generator Capacity", category, 1000, 100, 100000, "The Capacity for Mana a Mana Generator has");
        manaMachineManaCapacity = blocks.getInt("Mana Machine Capacity", category, 1000, 100, 100000, "The Mana Capacity for all Mana consuming Machines");
        manaGainFromMob = blocks.getInt("Mana From Mob", category, 10, 1, 1000, "Mana gained by inflicting 1HP damage to a Mob with the Mob Generator");

        category = "Ranges";
        blocks.addCustomCategoryComment(category, "Block Ranges");
        manaBlockTransferRange = blocks.getInt("Mana Transfer Range", category, 10, 1, 100, "The maximum Distance between Blocks to be able to transfer Mana");
        manaGeneratorMobRange = blocks.getInt("Mob Mana Generator Range", category, 5, 1, 100, "The Radius in which Mobs will be damaged to generate Mana");

        category = "Insanity Water";
        blocks.addCustomCategoryComment(category, "Insanity Water");
        insanityWaterEffect = blocks.getBoolean("Potion Effect", category, true, "Insanity Water gives Weakness and Speed Effect");

        category = "Demon Stone";
        blocks.addCustomCategoryComment(category, "Demon Stone");
        demonSummonCost = blocks.getInt("Demon Summon Costs", category, 100, 0, 10000, "Mana Costs to summon the Demon");
        demonSummonXP = blocks.getInt("Demon Summon XP", category, 100, 0, 10000, "Skill XP gained by summoning the Demon");
        requireDemonStructure = blocks.getBoolean("Require Demon Summoning Structure", category, true, "Require a Structure around the Demon Stone to Summon the Demon");

        category = "Magic Light";
        blocks.addCustomCategoryComment(category, "Magic Light");
        magicLightParticles = blocks.getInt("Magic Light Particles", category, 12, 0, 64, "The amount of Particles for Graphics: 'All Particles' ('Decreased Particles' uses half)");

        category = "Dwarf Base Marker";
        blocks.addCustomCategoryComment(category, "Dwarf Base Marker");
        dwarfBaseMarkerParticles = blocks.getInt("Dwarf Base Marker Particles", category, 4, 0, 16, "The Chance that Particles will spawn at a Dwarf Base Marker");
        dwarfMarkerSpawnChance = blocks.getInt("Dwarf Base Marker Spawn Chance", category, 4, 0, 16, "The Chance that Dwarfs will spawn near a Dwarf Base Marker");

        blocks.save();
    }
    public static void initItems(File file)
    {
        items = new Configuration(file);
        String category;

        category = "Mana Costs";
        items.addCustomCategoryComment(category, "Mana Costs");
        manaBoosterAmount = items.getInt("Mana Booster Amount", category, 1000, 1, 100000, "Mana Amount a Mana Booster gives");
        teleportationCrystalManaCost = items.getInt("Teleportation Crystal Cost", category, 90, 0, 10000, "Mana Costs for the Teleportation Crystal");
        spellBaseManaCost = items.getInt("Base Spell Cost", category, 10, 1, 1000, "The Costs for a Spell without effect");

        category = "Skill XP";
        items.addCustomCategoryComment(category, "Skill XP");
        teleportationCrystalSkillXP = items.getInt("Teleportation Crystal Skill XP", category, 5, 0, 10000, "Skill XP gained by using the Teleportation Crystal");

        items.save();
    }
}