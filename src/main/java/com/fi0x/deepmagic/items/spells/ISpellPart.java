package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import com.fi0x.deepmagic.items.spells.modifiers.ISpellModifier;
import com.fi0x.deepmagic.items.spells.trigger.ISpellTrigger;
import com.fi0x.deepmagic.items.spells.types.ISpellType;

public interface ISpellPart
{
    String getName();

    default boolean isType()
    {
        return false;
    }
    default boolean isTrigger()
    {
        return false;
    }
    default boolean isModifier()
    {
        return false;
    }
    default boolean isEffect()
    {
        return false;
    }

    default ISpellType getType()
    {
        return null;
    }
    default ISpellTrigger getTrigger()
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
}
