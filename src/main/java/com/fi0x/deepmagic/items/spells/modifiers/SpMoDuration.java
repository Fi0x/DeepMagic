package com.fi0x.deepmagic.items.spells.modifiers;

public class SpMoDuration implements ISpellModifier
{
    @Override
    public String getName()
    {
        return "modifier_duration";
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public int duration()
    {
        //TODO: Adjust value
        return 1;
    }
}
