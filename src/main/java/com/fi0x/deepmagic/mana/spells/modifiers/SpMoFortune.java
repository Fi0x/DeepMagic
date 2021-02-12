package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.mana.spells.ISpellPart;

public class SpMoFortune implements ISpellModifier
{
    public static final String NAME = "modifier_fortune";
    private final int FORTUNE_INCREASE = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Fortune Modifier";
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
        part.setFortune(part.getFortuneLvl() + FORTUNE_INCREASE);
        return part;
    }
}
