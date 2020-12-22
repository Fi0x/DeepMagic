package com.fi0x.deepmagic.items.spells.modifiers;

public class SpMoMiningPower implements ISpellModifier
{
    @Override
    public String getName()
    {
        return "modifier_miningpower";
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public int miningPower()
    {
        //TODO: Adjust value
        return 1;
    }
}
