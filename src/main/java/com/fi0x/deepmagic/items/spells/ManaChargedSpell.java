package com.fi0x.deepmagic.items.spells;

public class ManaChargedSpell extends SpellBase
{
    /*Spell that has no effect by itself
     * Can be crafted together with a spell that has an effect
     * Uses the effect of the crafted spell
     * Can't be powered with playermana
     * EITHER needst to be charged with playermana in advance
     * OR can be linked with machine mana*/
    public ManaChargedSpell(String name)
    {
        super(name, 1);
    }
}