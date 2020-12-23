package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

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
    public ISpellPart modifyPart(ISpellPart part)
    {
        //TODO: change value of spellpart
        return null;
    }
}
