package com.fi0x.deepmagic.items.spells.modifiers;

public class SpMoTickSpeed implements ISpellModifier
{
    @Override
    public String getName()
    {
        return "modifier_tickspeed";
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public int tickSpeed()
    {
        return 1;
    }
}
