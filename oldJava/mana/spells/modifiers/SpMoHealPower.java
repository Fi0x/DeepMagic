package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.ISpellPart;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class SpMoHealPower implements ISpellModifier
{
    public static final String NAME = "modifier_healpower";
    private final int HEAL_INCREASE = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Heal Modifier";
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
        list.add(new ItemStack(Items.GOLDEN_APPLE));

        return list;
    }

    @Override
    public double[] getCastModifiers()
    {
        return new double[]{0, 0.2, 0, 2};
    }

    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setHealPower(part.getHealPower() + HEAL_INCREASE);
        return part;
    }
}
