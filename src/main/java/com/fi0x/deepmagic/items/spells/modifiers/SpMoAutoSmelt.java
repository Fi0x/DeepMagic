package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

/*
 * Weather the spell can smelt blocks while mining or not
 */
public class SpMoAutoSmelt implements ISpellModifier
{
    public static final String NAME = "modifier_autosmelt";

    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setAutoSmelt(true);
        return part;
    }
}
