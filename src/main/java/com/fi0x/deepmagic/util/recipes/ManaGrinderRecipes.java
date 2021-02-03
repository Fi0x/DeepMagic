package com.fi0x.deepmagic.util.recipes;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import com.google.common.collect.Maps;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ManaGrinderRecipes
{
    private static final ManaGrinderRecipes INSTANCE = new ManaGrinderRecipes();
    private final Map<ItemStack, ItemStack> grinderList = Maps.newHashMap();

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

        addManaGrinderRecipe(new ItemStack(ModBlocks.INSANITY_COAL_ORE, 1), new ItemStack(Items.COAL, 2));
        addManaGrinderRecipe(new ItemStack(ModBlocks.INSANITY_DIAMOND_ORE, 1), new ItemStack(Items.DIAMOND, 2));
        addManaGrinderRecipe(new ItemStack(ModBlocks.INSANITY_EMERALD_ORE, 1), new ItemStack(Items.EMERALD, 2));
        addManaGrinderRecipe(new ItemStack(ModBlocks.INSANITY_DEEP_CRYSTAL_ORE, 1), new ItemStack(ModItems.DEEP_CRYSTAL_POWDER, 2));

        addManaGrinderRecipe(new ItemStack(ModItems.COOKED_WORM, 1), new ItemStack(ModItems.WORM_SNACK, 1));
        addManaGrinderRecipe(new ItemStack(ModItems.COOKED_COCKROACH, 1), new ItemStack(ModItems.COCKROACH_SNACK, 1));
        addManaGrinderRecipe(new ItemStack(Items.WHEAT, 1), new ItemStack(ModItems.WHEAT_FLOUR, 1));

        addManaGrinderRecipe(new ItemStack(Blocks.COAL_ORE, 1), new ItemStack(Items.COAL, 4));
        addManaGrinderRecipe(new ItemStack(Blocks.REDSTONE_ORE, 1), new ItemStack(Items.REDSTONE, 8));
        addManaGrinderRecipe(new ItemStack(Blocks.DIAMOND_ORE, 1), new ItemStack(Items.DIAMOND, 4));
        addManaGrinderRecipe(new ItemStack(Blocks.EMERALD_ORE, 1), new ItemStack(Items.EMERALD, 4));
        addManaGrinderRecipe(new ItemStack(Blocks.QUARTZ_ORE, 1), new ItemStack(Items.QUARTZ, 4));
    }

    private void addManaGrinderRecipe(ItemStack input, ItemStack result)
    {
        if(getGrinderResult(input) != ItemStack.EMPTY) return;
        grinderList.put(input, result);
    }

    public ItemStack getGrinderResult(ItemStack input)
    {
        for(Map.Entry<ItemStack, ItemStack> entry : grinderList.entrySet())
        {
            if(this.compareItemStacks(input, entry.getKey()))
            {
                return new ItemStack(entry.getValue().getItem(), entry.getValue().getCount());
            }
        }
        return ItemStack.EMPTY;
    }
    public Map<ItemStack, ItemStack> getRecipeMap()
    {
        return grinderList;
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
}