package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.mana.spells.ISpellPart;

/*
 * Weather the spell can smelt blocks while mining or not
 */
public class SpMoAutoSmelt implements ISpellModifier
{
    public static final String NAME = "modifier_autosmelt";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Auto-Smelt Modifier";
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
        part.setAutoSmelt(true);
        return part;
    }
}
