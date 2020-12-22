package com.fi0x.deepmagic.items.spells.modifiers;

public class SpMoDamage implements ISpellModifier
{
    @Override
    public String getName()
    {
        return "modifier_damage";
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public int damage()
    {
        //TODO: Adjust value
        return 1;
    }
}
