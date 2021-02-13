package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.ISpellPart;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class SpMoTickSpeed implements ISpellModifier
{
    public static final String NAME = "modifier_tickspeed";
    private final int TICK_INCREASE = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Tick-Speed Modifier";
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
        list.add(new ItemStack(Items.CLOCK));
        list.add(new ItemStack(ModItems.MAGIC_SIGIL));

        return list;
    }

    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setTickSpeed(part.getTickSpeed() + TICK_INCREASE);
        return part;
    }
}
