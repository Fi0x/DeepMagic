package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

/*
 * Splits types into multiple weaker ones
 */
public class SpMoSplit implements ISpellModifier
{
    private final int SPLIT_ADDER = 1;

    @Override
    public String getName()
    {
        return "modifier_split";
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
