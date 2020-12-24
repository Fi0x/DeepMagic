package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

public class SpMoMiningPower implements ISpellModifier
{
    private final int MINING_INCREASE = 1;

    @Override
    public String getName()
    {
        return "modifier_miningpower";
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setMiningPower(part.getMiningPower() + MINING_INCREASE);
        return part;
    }
}
