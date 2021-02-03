package com.fi0x.deepmagic.util.compat.jei;

import com.fi0x.deepmagic.blocks.containers.ContainerManaGrinder;
import com.fi0x.deepmagic.blocks.containers.ContainerManaInfuser;
import com.fi0x.deepmagic.gui.GuiManaGrinder;
import com.fi0x.deepmagic.gui.GuiManaInfuser;
import com.fi0x.deepmagic.util.compat.jei.managrinder.ManaGrinderRecipeCategory;
import com.fi0x.deepmagic.util.compat.jei.managrinder.ManaGrinderRecipeMaker;
import com.fi0x.deepmagic.util.compat.jei.manainfuser.ManaInfuserRecipeCategory;
import com.fi0x.deepmagic.util.compat.jei.manainfuser.ManaInfuserRecipeMaker;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nonnull;
import java.util.IllegalFormatException;

@JEIPlugin
public class JEICompat implements IModPlugin
{
    @Override
    public void registerCategories(@Nonnull IRecipeCategoryRegistration registry)
    {
        final IJeiHelpers helpers = registry.getJeiHelpers();
        final IGuiHelper gui = helpers.getGuiHelper();

        registry.addRecipeCategories(new ManaInfuserRecipeCategory(gui));
        registry.addRecipeCategories(new ManaGrinderRecipeCategory(gui));
    }
    @Override
    public void register(@Nonnull IModRegistry registry)
    {
        final IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
        final IJeiHelpers helpers = registry.getJeiHelpers();
        IRecipeTransferRegistry recipeTransfer = registry.getRecipeTransferRegistry();

        registry.addRecipes(ManaInfuserRecipeMaker.getRecipes(helpers), RecipeCategories.MANA_INFUSER);
        registry.addRecipeClickArea(GuiManaInfuser.class, 41, 22, 40, 22, RecipeCategories.MANA_INFUSER);
        recipeTransfer.addRecipeTransferHandler(ContainerManaInfuser.class, RecipeCategories.MANA_INFUSER, 0, 1, 2, 36);

        registry.addRecipes(ManaGrinderRecipeMaker.getRecipes(helpers), RecipeCategories.MANA_GRINDER);
        registry.addRecipeClickArea(GuiManaGrinder.class, 41, 22, 40, 22, RecipeCategories.MANA_GRINDER);
        recipeTransfer.addRecipeTransferHandler(ContainerManaGrinder.class, RecipeCategories.MANA_GRINDER, 0, 1, 4, 36);
    }

    public static String translateToLocal(String key)
    {
        if(I18n.canTranslate(key)) return I18n.translateToLocal(key);
        else return I18n.translateToFallback(key);
    }
    public static String translateToLocalFormatted(String key, Object... format)
    {
        String s = translateToLocal(key);
        try
        {
            return String.format(s, format);
        } catch(IllegalFormatException e)
        {
            return "Format error: " + s;
        }
    }
}
