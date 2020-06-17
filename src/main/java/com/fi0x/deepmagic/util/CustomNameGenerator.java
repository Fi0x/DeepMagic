package com.fi0x.deepmagic.util;

import net.minecraft.util.ResourceLocation;

public class CustomNameGenerator
{
    private static final String[] TROLL_NAMES = new String[] {"Kevin", "Bob", "Karl", "Bronn", "Mike", "Steve", "Jeff"};

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
            {"deepmagic", "Mage"}};

    public static String getRandomTrollName()
    {
        return TROLL_NAMES[(int) (Math.random() * TROLL_NAMES.length)];
    }

    public static ResourceLocation getRandomSpawnableMob()
    {
        int mob = (int) (Math.random() * SPAWNER_TYPES.length);
        return new ResourceLocation(SPAWNER_TYPES[mob][0], SPAWNER_TYPES[mob][1]);
    }
}