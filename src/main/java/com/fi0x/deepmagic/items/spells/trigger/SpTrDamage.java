package com.fi0x.deepmagic.items.spells.trigger;

public class SpTrDamage implements ISpellTrigger
{
    @Override
    public String getName()
    {
        return "trigger_damage";
    }
    @Override
    public ISpellTrigger getTrigger()
    {
        return this;
    }
    //TODO: Add damage event handler
}
