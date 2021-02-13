package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.ISpellPart;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/*
 * Weather the spell does environmental damage or not
 */
public class SpMoEnvironmental implements ISpellModifier
{
    public static final String NAME = "modifier_environmental";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Env. Dmg. Modifier";
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
        list.add(new ItemStack(Items.IRON_SHOVEL));
        list.add(new ItemStack(Items.IRON_PICKAXE));
        list.add(new ItemStack(Items.IRON_AXE));

        return list;
    }

    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setEnvironmentalDmg(true);
        return part;
    }
}
