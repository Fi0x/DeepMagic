package com.fi0x.deepmagic.items.mana;

import com.fi0x.deepmagic.util.IMagicItem;

public class ManaChargedSpell extends Spell implements IMagicItem
{
    /* Spell that has no effect by itself
     * Can be crafted together with a spell that has an effect
     * Uses the effect of the crafted spell
     * Can't be powered with playermana
     * EITHER needst to be charged with playermana in advance
     * OR can be linked with machine mana*/

    public ManaChargedSpell(String name, SpellType spellType, int tier, int manaCost)
    {
        super(name, spellType, tier, manaCost);
    }
}