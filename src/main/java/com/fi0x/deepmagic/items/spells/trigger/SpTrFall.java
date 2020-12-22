package com.fi0x.deepmagic.items.spells.trigger;

public class SpTrFall implements ISpellTrigger
{
    @Override
    public String getName()
    {
        return "trigger_fall";
    }
    @Override
    public ISpellTrigger getTrigger()
    {
        return this;
    }
    //TODO: Add fall event handler
}
