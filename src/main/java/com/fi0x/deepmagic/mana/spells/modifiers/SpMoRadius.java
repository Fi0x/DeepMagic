package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.mana.spells.ISpellPart;

public class SpMoRadius implements ISpellModifier
{
    public static final String NAME = "modifier_radius";
    private final double RADIUS_INCREASE = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Radius Modifier";
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
        part.setRadius(part.getRadius() + RADIUS_INCREASE);
        return part;
    }
}
