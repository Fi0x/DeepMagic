package com.fi0x.deepmagic.util.compat.jei.manainfuser;

import com.fi0x.deepmagic.util.recipes.ManaInfuserRecipes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ManaInfuserRecipe implements IRecipeWrapper
{
    private final ItemStack input;
    private final ItemStack output;

    public ManaInfuserRecipe(ItemStack input, ItemStack output)
    {
        this.input = input;
        this.output = output;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        ingredients.setInput(ItemStack.class, input);
        ingredients.setOutput(ItemStack.class, output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
    {
        ManaInfuserRecipes recipes = ManaInfuserRecipes.instance();
    }
}
