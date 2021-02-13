package com.fi0x.deepmagic.mana.spells.modifiers;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.ISpellPart;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class SpMoLooting implements ISpellModifier
{
    public static final String NAME = "modifier_looting";
    private final int LOOTING_INCREASE = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Looting Modifier";
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
        list.add(new ItemStack(Blocks.LAPIS_BLOCK));
        list.add(new ItemStack(Items.DIAMOND));

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
        part.setLootingLvl(part.getLootingLvl() + LOOTING_INCREASE);
        return part;
    }
}
