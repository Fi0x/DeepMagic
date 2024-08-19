package com.fi0x.deepmagic.util.compat.jei.manainfuser;

import com.fi0x.deepmagic.util.recipes.ManaInfuserRecipes;
import com.google.common.collect.Lists;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;

public class ManaInfuserRecipeMaker
{
    public static List<ManaInfuserRecipe> getRecipes(IJeiHelpers helpers)
    {
        IStackHelper stackHelper = helpers.getStackHelper();
        ManaInfuserRecipes instance = ManaInfuserRecipes.instance();
        Map<ItemStack, ItemStack> recipes = instance.getRecipeMap();
        List<ManaInfuserRecipe> jeiRecipes = Lists.newArrayList();

        for(Map.Entry<ItemStack, ItemStack> entry : recipes.entrySet())
        {
            ItemStack input = entry.getKey();
            ItemStack output = entry.getValue();
            ManaInfuserRecipe recipe = new ManaInfuserRecipe(input, output);
            jeiRecipes.add(recipe);
        }
        return jeiRecipes;
    }
}
