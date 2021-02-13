package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.ISpellPart;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/*
 * Weather the spell can smelt blocks while mining or not
 */
public class SpMoAutoSmelt implements ISpellModifier
{
    public static final String NAME = "modifier_autosmelt";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Auto-Smelt Modifier";
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
        list.add(new ItemStack(Items.LAVA_BUCKET));

        return list;
    }

    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setAutoSmelt(true);
        return part;
    }
}
