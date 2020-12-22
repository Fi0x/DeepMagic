package com.fi0x.deepmagic.items.spells.modifiers;

public class SpMoRange implements ISpellModifier
{
    @Override
    public String getName()
    {
        return "modifier_range";
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public int range()
    {
        //TODO: Adjust value
        return 1;
    }
}
