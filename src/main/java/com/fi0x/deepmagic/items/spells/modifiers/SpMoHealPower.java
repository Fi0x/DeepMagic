package com.fi0x.deepmagic.items.spells.modifiers;

public class SpMoHealPower implements ISpellModifier
{
    @Override
    public String getName()
    {
        return "modifier_healpower";
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public int healPower()
    {
        //TODO: Adjust value
        return 1;
    }
}
