package com.fi0x.deepmagic.items.spells.modifiers;

/*
 * Splits types into multiple weaker ones
 */
public class SpMoSplit implements ISpellModifier
{
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
    public int split()
    {
        //TODO: Adjust value
        return 1;
    }
}
