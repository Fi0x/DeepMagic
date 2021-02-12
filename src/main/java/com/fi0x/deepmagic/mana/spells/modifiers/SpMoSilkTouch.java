package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.mana.spells.ISpellPart;

/*
 * Weather the spell has Silk-Touch or not
 */
public class SpMoSilkTouch implements ISpellModifier
{
    public static final String NAME = "modifier_silktouch";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Silk-Touch Modifier";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME;
        return ret;
    }
    @Override
    public ISpellModifier getModifier()
    {
        return this;
    }
    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setSilkTouch(true);
        return part;
    }
}
