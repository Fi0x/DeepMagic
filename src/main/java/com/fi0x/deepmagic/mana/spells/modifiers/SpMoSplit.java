package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.mana.spells.ISpellPart;

/*
 * Splits types into multiple weaker ones
 */
public class SpMoSplit implements ISpellModifier
{
    public static final String NAME = "modifier_split";
    private final int SPLIT_ADDER = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Split Modifier";
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
        part.setSplit(part.getSplit() + SPLIT_ADDER);
        return part;
    }
}
