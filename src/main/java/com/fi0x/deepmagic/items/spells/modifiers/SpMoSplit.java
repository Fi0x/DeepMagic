package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

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
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setSplit(part.getSplit() + SPLIT_ADDER);
        return part;
    }
}
