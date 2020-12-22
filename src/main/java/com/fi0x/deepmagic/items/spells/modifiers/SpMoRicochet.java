package com.fi0x.deepmagic.items.spells.modifiers;

public class SpMoRicochet implements ISpellModifier
{
    @Override
    public String getName()
    {
        return "modifier_ricochet";
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public boolean ricochet()
    {
        return true;
    }
}
