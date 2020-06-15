package com.fi0x.deepmagic.util;

public class CustomNameGenerator
{
    private static final String[] TROLL_NAMES = new String[] {"Kevin", "Bob", "Karl", "Bronn", "Mike", "Steve", "Jeff"};

    public static String getRandomTrollName()
    {
        return TROLL_NAMES[(int) (Math.random() * TROLL_NAMES.length)];
    }
}