package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

public class SpMoDamage implements ISpellModifier
{
    public static final String NAME = "modifier_damage";
    private final int DAMAGE_INCREASE = 1;

    @Override
    public String getName()
    {
        return NAME;
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
        part.setDamage(part.getDamage() + DAMAGE_INCREASE);
        return part;
    }
}
