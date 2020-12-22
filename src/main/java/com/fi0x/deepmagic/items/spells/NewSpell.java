package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.util.IMagicItem;

import java.util.ArrayList;

public class NewSpell extends ItemBase implements IMagicItem
{
    protected final ArrayList<ISpellPart> spellParts = new ArrayList<>();

    public NewSpell(String name)
    {
        super(name);
        setCreativeTab(DeepMagicTab.SPELLS);
        setMaxStackSize(1);
    }
}
