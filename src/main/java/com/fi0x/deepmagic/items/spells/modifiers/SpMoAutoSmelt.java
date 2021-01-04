package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

/*
 * Weather the spell can smelt blocks while mining or not
 */
public class SpMoAutoSmelt implements ISpellModifier
{
    @Override
    public String getName()
    {
        return "modifier_autosmelt";
    }
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
