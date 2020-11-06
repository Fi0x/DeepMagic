package com.fi0x.deepmagic.util.recipes;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ManaInfuserRecipes
{
    private static final ManaInfuserRecipes INSTANCE = new ManaInfuserRecipes();
    private final Map<ItemStack, ItemStack> infusionList = Maps.newHashMap();

    public static ManaInfuserRecipes instance()
    {
        return INSTANCE;
    }
    private ManaInfuserRecipes()
    {
        addManaInfuserRecipe(new ItemStack(ModItems.DEEP_CRYSTAL_POWDER, 1), new ItemStack(ModItems.MAGIC_POWDER, 1));
        addManaInfuserRecipe(new ItemStack(ModBlocks.DEEP_CRYSTAL_BLOCK, 1), new ItemStack(ModBlocks.DEMON_CRYSTAL_BLOCK, 1));
    }

    private void addManaInfuserRecipe(ItemStack input, ItemStack result)
    {
        if(getInfuserResult(input) != ItemStack.EMPTY) return;
        infusionList.put(input, result);
    }

    public ItemStack getInfuserResult(ItemStack input)
    {
        for (Map.Entry<ItemStack, ItemStack> entry : infusionList.entrySet())
        {
            if (this.compareItemStacks(input, entry.getKey()))
            {
                return new ItemStack(entry.getValue().getItem(), entry.getValue().getCount());
            }
        }
        return ItemStack.EMPTY;
    }
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
}