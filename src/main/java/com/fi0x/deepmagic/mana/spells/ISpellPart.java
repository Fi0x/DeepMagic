package com.fi0x.deepmagic.mana.spells;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public interface ISpellPart
{
    String getName();
    String getDisplayName();
    String getPartAsString();
    default void setAttributesFromString(ArrayList<String> attributes)
    {
    }
    ArrayList<ItemStack> getRequiredItems();

    default void setAutoSmelt(boolean state)
    {
    }
    default boolean hasAutoSmelt()
    {
        return false;
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
    default void setEnvironmentalDmg(boolean state)
    {
    }
    default boolean doesEnvironmentalDmg()
    {
        return false;
    }
    default void setFortune(int level)
    {
    }
    default int getFortuneLvl()
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
    default void setLootingLvl(int level)
    {
    }
    default int getLootingLvl()
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
    default void setSilkTouch(boolean state)
    {
    }
    default boolean hasSilkTouch()
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
    default void setVelocity(double value)
    {
    }
    default double getVelocity()
    {
        return 0;
    }
}
