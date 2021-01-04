package com.fi0x.deepmagic.items.spells.modifiers;

import com.fi0x.deepmagic.items.spells.ISpellPart;

/*
 * Weather the spell does environmental damage or not
 */
public class SpMoSilkTouch implements ISpellModifier
{
    @Override
    public String getName()
    {
        return "modifier_silktouch";
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
