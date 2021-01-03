package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

/*
 * Weather the spell does environmental damage or not
 */
public class SpMoEnvironmental implements ISpellModifier
{
    @Override
    public String getName()
    {
        return "modifier_environmental";
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setEnvironmentalDmg(true);
        return part;
    }
}
