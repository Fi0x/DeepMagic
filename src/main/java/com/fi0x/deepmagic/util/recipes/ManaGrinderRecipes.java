package com.fi0x.deepmagic.util.recipes;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ManaGrinderRecipes
{
    private static final ManaGrinderRecipes INSTANCE = new ManaGrinderRecipes();
    private final Map<ItemStack, ItemStack> infusionList = Maps.newHashMap();

    public static ManaGrinderRecipes instance()
    {
        return INSTANCE;
    }
    private ManaGrinderRecipes()
    {
        addManaGrinderRecipe(new ItemStack(ModItems.DEEP_CRYSTAL, 1), new ItemStack(ModItems.DEEP_CRYSTAL_POWDER, 1));
        addManaGrinderRecipe(new ItemStack(ModBlocks.DEEP_CRYSTAL_ORE, 1), new ItemStack(ModItems.DEEP_CRYSTAL_POWDER, 2));
        addManaGrinderRecipe(new ItemStack(ModBlocks.DEEP_CRYSTAL_END_ORE, 1), new ItemStack(ModItems.DEEP_CRYSTAL_POWDER, 2));
        addManaGrinderRecipe(new ItemStack(ModBlocks.DEEP_CRYSTAL_NETHER_ORE, 1), new ItemStack(ModItems.DEEP_CRYSTAL_POWDER, 2));
    }

    private void addManaGrinderRecipe(ItemStack input, ItemStack result)
    {
        if(getGrinderResult(input) != ItemStack.EMPTY) return;
        infusionList.put(input, result);
    }

    public ItemStack getGrinderResult(ItemStack input)
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