package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

public class SpMoRicochet implements ISpellModifier
{
    public static final String NAME = "modifier_ricochet";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setRicochet(true);
        return part;
    }
}
