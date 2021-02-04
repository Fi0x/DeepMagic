package com.fi0x.deepmagic.util.compat.jei.managrinder;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.Reference;
import com.fi0x.deepmagic.util.compat.jei.RecipeCategories;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManaGrinderRecipeCategory extends AbstractManaGrinderRecipeCategory<ManaGrinderRecipe>
{
    private final IDrawable background;
    private final String name;
    private final IDrawable icon;

    public ManaGrinderRecipeCategory(IGuiHelper helper)
    {
        super(helper);
        background = helper.createDrawable(TEXTURES, 11, 8, 161, 66);
        name = "Mana Grinder";
        icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.MANA_GRINDER));
    }

    @Nonnull
    @Override
    public IDrawable getBackground()
    {
        return background;
    }
    @Nullable
    @Override
    public IDrawable getIcon()
    {
        return icon;
    }
    @Override
    public void drawExtras(@Nonnull Minecraft minecraft)
    {
        animatedProcess.draw(minecraft, 31, 17);
        animatedMana.draw(minecraft, 136, 8);
    }
    @Nonnull
    @Override
    public String getTitle()
    {
        return name;
    }
    @Nonnull
    @Override
    public String getModName()
    {
        return Reference.NAME;
    }
    @Nonnull
    @Override
    public String getUid()
    {
        return RecipeCategories.MANA_GRINDER;
    }
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @Nonnull ManaGrinderRecipe recipeWrapper, @Nonnull IIngredients ingredients)
    {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        stacks.init(input, true, 9, 16);
        stacks.init(output, false, 74, 16);
        stacks.set(ingredients);
    }
}
