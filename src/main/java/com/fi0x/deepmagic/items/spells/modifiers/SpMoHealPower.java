package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

public class SpMoHealPower implements ISpellModifier
{
    public static final String NAME = "modifier_healpower";
    private final int HEAL_INCREASE = 1;

    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setHealPower(part.getHealPower() + HEAL_INCREASE);
        return part;
    }
}
