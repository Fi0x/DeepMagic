package com.fi0x.deepmagic.util.compat.jei.managrinder;

import com.fi0x.deepmagic.util.recipes.ManaGrinderRecipes;
import com.google.common.collect.Lists;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;

public class ManaGrinderRecipeMaker
{
    public static List<ManaGrinderRecipe> getRecipes(IJeiHelpers helpers)
    {
        IStackHelper stackHelper = helpers.getStackHelper();
        ManaGrinderRecipes instance = ManaGrinderRecipes.instance();
        Map<ItemStack, ItemStack> recipes = instance.getRecipeMap();
        List<ManaGrinderRecipe> jeiRecipes = Lists.newArrayList();

        for(Map.Entry<ItemStack, ItemStack> entry : recipes.entrySet())
        {
            ItemStack input = entry.getKey();
            ItemStack output = entry.getValue();
            ManaGrinderRecipe recipe = new ManaGrinderRecipe(input, output);
            jeiRecipes.add(recipe);
        }
        return jeiRecipes;
    }
}
