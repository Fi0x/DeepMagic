package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

public class SpMoHealPower implements ISpellModifier
{
    private final int HEAL_INCREASE = 1;

    @Override
    public String getName()
    {
        return "modifier_healpower";
    }
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
