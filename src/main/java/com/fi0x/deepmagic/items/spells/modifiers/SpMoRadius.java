package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

public class SpMoRadius implements ISpellModifier
{
    private final double RADIUS_INCREASE = 1;

    @Override
    public String getName()
    {
        return "modifier_radius";
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
