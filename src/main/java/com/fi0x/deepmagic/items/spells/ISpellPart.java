package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import com.fi0x.deepmagic.items.spells.modifiers.ISpellModifier;
import com.fi0x.deepmagic.items.spells.types.ISpellType;

public interface ISpellPart
{
    String getName();

    default ISpellType getType()
    {
        return null;
    }
    default ISpellModifier getModifier()
    {
        return null;
    }
    default ISpellEffect getEffect()
    {
        return null;
    }

    default void setDamage(int value)
    {
    }
    default int getDamage()
    {
        return 0;
    }
    default void setDuration(double value)
    {
    }
    default double getDuration()
    {
        return 0;
    }
    default void setGravity(boolean state)
    {
    }
    default boolean hasGravity()
    {
        return false;
    }
    default void setHealPower(int value)
    {
    }
    default int getHealPower()
    {
        return 0;
    }
    default void setPower(int value)
    {
    }
    default int getPower()
    {
        return 0;
    }
    default void setPiercing(boolean state)
    {
    }
    default boolean hasPiercing()
    {
        return false;
    }
    default void setRadius(double value)
    {
    }
    default double getRadius()
    {
        return 0;
    }
    default void setRange(double value)
    {
    }
    default double getRange()
    {
        return 0;
    }
    default void setRicochet(boolean state)
    {
    }
    default boolean canRicochet()
    {
        return false;
    }
    default void setSplit(int value)
    {
    }
    default int getSplit()
    {
        return 0;
    }
    default void setTickSpeed(int value)
    {
    }
    default int getTickSpeed()
    {
        return 0;
    }
}
