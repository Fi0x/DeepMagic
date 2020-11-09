package com.fi0x.deepmagic.util;

import net.minecraft.util.ResourceLocation;

public class CustomNameGenerator
{
    private static final String[] TROLL_NAMES = new String[] {"Ali", "Bob", "Brian", "Bronn", "Erik", "Jeff", "Jeremy", "Karl", "Kevin", "Mike", "Sebastian", "Steve"};
    private static final String[] MAGE_NAMES = new String[] {"Anagast", "Astrea", "Dubin", "Durin", "Druviar", "Ebras", "Fharin", "Gandalf", "Kraldor", "Malfurion", "Masior", "Merlin", "Ogrus", "Orhan", "Seharis", "Tholyn", "Thrall", "Torin", "Udor", "Ugafeus", "Vogorim"};

    private static final String[][] SPAWNER_TYPES = new String[][] {
            {"minecraft", "Creeper"},
            {"minecraft", "Zombie"},
            {"minecraft", "Spider"},
            {"minecraft", "Skeleton"},
            {"minecraft", "Husk"},
            {"minecraft", "Slime"},
            {"minecraft", "Witch"},
            {"minecraft", "Wither_Skeleton"},
            {"deepmagic", "Rock_Troll"},
            {"deepmagic", "Giant"},
            {"deepmagic", "Hovering_Orb"},
            {"deepmagic", "Depth_Mage"}};

    public static String getRandomTrollName()
    {
        return TROLL_NAMES[(int) (Math.random() * TROLL_NAMES.length)];
    }
    public static String getRandomMageName()
    {
        return MAGE_NAMES[(int) (Math.random() * MAGE_NAMES.length)];
    }

    public static ResourceLocation getRandomSpawnableMob()
    {
        int mob = (int) (Math.random() * SPAWNER_TYPES.length);
        return new ResourceLocation(SPAWNER_TYPES[mob][0], SPAWNER_TYPES[mob][1]);
    }
}