package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

public class SpMoTickSpeed implements ISpellModifier
{
    private final int TICK_INCREASE = 1;

    @Override
    public String getName()
    {
        return "modifier_tickspeed";
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setTickSpeed(part.getTickSpeed() + TICK_INCREASE);
        return part;
    }
}
