package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.mana.spells.ISpellPart;

public class SpMoRange implements ISpellModifier
{
    public static final String NAME = "modifier_range";
    private final double RANGE_INCREASE = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Range Modifier";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME;
        return ret;
    }
    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setRange(part.getRange() + RANGE_INCREASE);
        return part;
    }
}
