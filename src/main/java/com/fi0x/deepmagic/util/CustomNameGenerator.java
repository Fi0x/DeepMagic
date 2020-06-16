package com.fi0x.deepmagic.util;

public class CustomNameGenerator
{
    private static final String[] TROLL_NAMES = new String[] {"Kevin", "Bob", "Karl", "Bronn", "Mike", "Steve", "Jeff"};

    private static final String[] SPAWNER_TYPES = new String[] {"Zombie"};

    public static String getRandomTrollName()
    {
        return TROLL_NAMES[(int) (Math.random() * TROLL_NAMES.length)];
    }

    public static String getRandomSpawnableMob()
    {
        return SPAWNER_TYPES[(int) (Math.random() * SPAWNER_TYPES.length)];
    }
}