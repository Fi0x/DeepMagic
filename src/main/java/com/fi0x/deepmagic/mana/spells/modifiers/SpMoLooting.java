package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.mana.spells.ISpellPart;

public class SpMoLooting implements ISpellModifier
{
    public static final String NAME = "modifier_looting";
    private final int LOOTING_INCREASE = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Looting Modifier";
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
        part.setLootingLvl(part.getLootingLvl() + LOOTING_INCREASE);
        return part;
    }
}
