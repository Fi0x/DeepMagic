package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.mana.spells.ISpellPart;

public interface ISpellModifier extends ISpellPart
{
    ISpellPart modifyPart(ISpellPart part);
}
