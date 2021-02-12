package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.mana.spells.ISpellPart;

public class SpMoPower implements ISpellModifier
{
    public static final String NAME = "modifier_power";
    private final int MINING_INCREASE = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Power Modifier";
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
        part.setPower(part.getPower() + MINING_INCREASE);
        return part;
    }
}
