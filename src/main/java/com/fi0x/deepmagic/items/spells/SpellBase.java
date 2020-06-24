package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.util.IMagicItem;

public class SpellBase extends ItemBase implements IMagicItem
{
    protected int manaCost;
    protected int tier;

    public SpellBase(String name, int tier)
    {
        super(name);
        this.tier = tier;
        setMaxStackSize(1);
    }
}