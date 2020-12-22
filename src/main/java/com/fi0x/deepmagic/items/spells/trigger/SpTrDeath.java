package com.fi0x.deepmagic.items.spells.trigger;

public class SpTrDeath implements ISpellTrigger
{
    @Override
    public String getName()
    {
        return "trigger_death";
    }
    @Override
    public ISpellTrigger getTrigger()
    {
        return this;
    }
    //TODO: Add death event handler
}
