package com.fi0x.deepmagic.items.skillpoints;

import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.util.IMagicItem;

public class SkillpointBasic extends ItemBase implements IMagicItem
{
    public SkillpointBasic(String name)
    {
        super(name);
        setMaxStackSize(1);
    }
}