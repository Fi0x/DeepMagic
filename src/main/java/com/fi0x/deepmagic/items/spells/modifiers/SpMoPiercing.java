package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

public class SpMoPiercing implements ISpellModifier
{
    @Override
    public String getName()
    {
        return "modifier_piercing";
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setPiercing(true);
        return part;
    }
}