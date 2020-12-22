package com.fi0x.deepmagic.items.spells.modifiers;

public class SpMoGravity implements ISpellModifier
{
    @Override
    public String getName()
    {
        return "modifier_gravity";
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public boolean gravity()
    {
        return true;
    }
}
