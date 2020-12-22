package com.fi0x.deepmagic.items.spells.trigger;

public class SpTrHeal implements ISpellTrigger
{
    @Override
    public String getName()
    {
        return "trigger_heal";
    }
    @Override
    public ISpellTrigger getTrigger()
    {
        return this;
    }
    //TODO: Add heal event handler
}
