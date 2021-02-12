package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.mana.spells.ISpellPart;

public class SpMoDuration implements ISpellModifier
{
    public static final String NAME = "modifier_duration";
    private final int DURATION_INCREASE = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Duration Modifier";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME;
        return ret;
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setDuration(part.getDuration() + DURATION_INCREASE);
        return part;
    }
}
