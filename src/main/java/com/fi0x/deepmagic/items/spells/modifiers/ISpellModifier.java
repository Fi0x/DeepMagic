package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

public interface ISpellModifier extends ISpellPart
{
    default int damage()
    {
        return 0;
    }
    default int duration()
    {
        return 0;
    }
    default boolean gravity()
    {
        return false;
    }
    default int healPower()
    {
        return 0;
    }
    default int miningPower()
    {
        return 0;
    }
    default boolean piercing()
    {
        return false;
    }
    default int radius()
    {
        return 0;
    }
    default int range()
    {
        return 0;
    }
    default boolean ricochet()
    {
        return false;
    }
    default int split()
    {
        return 0;
    }
    default int tickSpeed()
    {
        return 0;
    }
}
