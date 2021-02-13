package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.ISpellPart;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class SpMoRange implements ISpellModifier
{
    public static final String NAME = "modifier_range";
    private final double RANGE_INCREASE = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Range Modifier";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME;
        return ret;
    }
    @Override
    public ArrayList<ItemStack> getRequiredItems()
    {
        ArrayList<ItemStack> list = new ArrayList<>();

        list.add(new ItemStack(ModItems.MAGIC_POWDER));
        list.add(new ItemStack(Items.ARROW));

        return list;
    }

    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setRange(part.getRange() + RANGE_INCREASE);
        return part;
    }
}
