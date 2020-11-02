package com.fi0x.deepmagic.util.handlers;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.util.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class ConfigHandler
{
    public static Configuration ids;
    public static Configuration worldgeneration;
    public static Configuration npcs;
    public static Configuration player;

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
    //Whitelist
    public static boolean dwarfMineOres;
    public static boolean dwarfMineResources;

    //PlayerMana
    public static int baseMana;
    public static int manaXPForLevelup;

    public static void registerConfig(FMLPreInitializationEvent event)
    {
        Main.config = new File(event.getModConfigurationDirectory() + "/" + Reference.MOD_ID);
        Main.config.mkdirs();
        initIDs(new File(Main.config.getPath(), "ModIDs.cfg"));
        initWorldGen(new File(Main.config.getPath(), "ModWorldGen.cfg"));
        initNPCs(new File(Main.config.getPath(), "ModNPCs.cfg"));
        initPlayer(new File(Main.config.getPath(), "Player.cfg"));
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

        ids.save();
    }
    public static void initWorldGen(File file)
    {
        worldgeneration = new Configuration(file);
        String category;

        category = "Ore Generation";
        worldgeneration.addCustomCategoryComment(category, "Enable Ore Generation");
        spawnDeepCrystalOre = worldgeneration.getBoolean("Deep Crystal Ore", category, true, "Enables Deep Crystal Ore Generation in Default Dimensions");

        category = "Structure Generation";
        worldgeneration.addCustomCategoryComment(category, "Structure Generation");
        generateMageHouses = worldgeneration.getBoolean("Mage Houses", category, true, "Enables Mage Houses in the Insanity Dimension");
        generateInsanityRockTrollCaves = worldgeneration.getBoolean("Insanity Rock Troll Caves", category, true, "Enables Rock Troll Caves in the Insanity Dimension");
        generateShrines = worldgeneration.getBoolean("Shrines", category, true, "Enables Shrines in the Insanity Dimension");
        generateInsanityOases = worldgeneration.getBoolean("Insanity Oases", category, true, "Enables Oases in the Insanity Dimension");
        generateDwarfBases = worldgeneration.getBoolean("Dwarf Bases", category, true, "Enables Dwarf Bases in the Insanity Dimension");
        generateDragonLairs = worldgeneration.getBoolean("Dragon Lairs", category, true, "Enables Dragon Lairs in the Insanity Dimension");
        generateDungeons = worldgeneration.getBoolean("Dungeons", category, true, "Enables Dungeons in the Insanity Dimension");

        worldgeneration.save();
    }
    public static void initNPCs(File file)
    {
        npcs = new Configuration(file);
        String category;

        category = "Insanity Biome Spawns";
        npcs.addCustomCategoryComment(category, "Insanity Biome Spawns");
        allowCockroach = npcs.getBoolean("Allow Cockroach", category, true, "");
        allowInsanityCow = npcs.getBoolean("Allow Insanity Cow", category, true, "");
        allowDepthMage = npcs.getBoolean("Allow Depth Mage", category, true, "");
        allowHoveringOrb = npcs.getBoolean("Allow Hovering Orb", category, true, "");
        allowGiant = npcs.getBoolean("Allow Giant", category, true, "");

        category = "NPC Behavior";
        npcs.addCustomCategoryComment(category, "NPC Behavior");
        dwarfMining = npcs.getBoolean("Dwarf Mining", category, true, "Allow Dwarfs to dig tunnels");

        category = "NPC Whitelist";
        npcs.addCustomCategoryComment(category, "NPC Whitelist");
        dwarfMineOres = npcs.getBoolean("Dwarf Mine Ores", category, true, "Allow Dwarfs to mine Ores");
        dwarfMineResources = npcs.getBoolean("Dwarf Mine Resources", category, true, "Allow Dwarfs to mine Resource Blocks");

        npcs.save();
    }
    public static void initPlayer(File file)
    {
        player = new Configuration(file);
        String category;

        category = "Mana";
        player.addCustomCategoryComment(category, "Mana");
        baseMana = player.getInt("Base Mana", category, 100, 1, 10000, "The Mana Capacity a new Player has");
        manaXPForLevelup = player.getInt("XP/Skillpoint", category, 100, 1, 10000, "The XP amount needed to get a Skillpoint");

        player.save();
    }
}