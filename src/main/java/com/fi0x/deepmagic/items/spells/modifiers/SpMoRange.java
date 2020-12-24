package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

public class SpMoRange implements ISpellModifier
{
    private final double RANGE_INCREASE = 1;

    @Override
    public String getName()
    {
        return "modifier_range";
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setRange(part.getRange() + RANGE_INCREASE);
        return part;
    }
}
