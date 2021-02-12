package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.mana.spells.ISpellPart;

/*
 * Weather the spell does environmental damage or not
 */
public class SpMoEnvironmental implements ISpellModifier
{
    public static final String NAME = "modifier_environmental";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Env. Dmg. Modifier";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME;
        return ret;
    }
    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setEnvironmentalDmg(true);
        return part;
    }
}
