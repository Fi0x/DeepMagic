package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

public class SpMoLooting implements ISpellModifier
{
    private final int LOOTING_INCREASE = 1;

    @Override
    public String getName()
    {
        return "modifier_looting";
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setLootingLvl(part.getLootingLvl() + LOOTING_INCREASE);
        return part;
    }
}
