package com.fi0x.deepmagic.items.spells.modifiers;

public class SpMoPiercing implements ISpellModifier
{
    @Override
    public String getName()
    {
        return "modifier_piercing";
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public boolean piercing()
    {
        return true;
    }
}
