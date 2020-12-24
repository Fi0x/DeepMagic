package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

public class SpMoDuration implements ISpellModifier
{
    private final int DURATION_INCREASE = 1;

    @Override
    public String getName()
    {
        return "modifier_duration";
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
