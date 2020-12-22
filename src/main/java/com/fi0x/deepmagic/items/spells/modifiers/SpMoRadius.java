package com.fi0x.deepmagic.items.spells.modifiers;

public class SpMoRadius implements ISpellModifier
{
    @Override
    public String getName()
    {
        return "modifier_radius";
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public int radius()
    {
        return 1;
    }
}
