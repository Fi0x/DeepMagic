package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.ISpellPart;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/*
 * Weather the spell has Silk-Touch or not
 */
public class SpMoSilkTouch implements ISpellModifier
{
    public static final String NAME = "modifier_silktouch";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Silk-Touch Modifier";
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
        //TODO: Recipe missing

        return list;
    }

    @Override
    public double[] getCastModifiers()
    {
        return new double[]{0, 0.5, 0, 4};
    }

    @Override
    public ISpellPart modifyPart(ISpellPart part)
    {
        part.setSilkTouch(true);
        return part;
    }
}
