package com.fi0x.deepmagic.util.compat.jei.manainfuser;

import com.fi0x.deepmagic.util.Reference;
import com.fi0x.deepmagic.util.compat.jei.RecipeCategories;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;

import javax.annotation.Nonnull;

public class ManaInfuserRecipeCategory extends AbstractManaInfuserRecipeCategory<ManaInfuserRecipe>
{
    private final IDrawable background;
    private final String name;

    public ManaInfuserRecipeCategory(IGuiHelper helper)
    {
        super(helper);
        background = helper.createDrawable(TEXTURES, 4, 12, 150, 60);
        name = "Mana Infuser";
    }

    @Nonnull
    @Override
    public IDrawable getBackground()
    {
        return background;
    }
    @Override
    public void drawExtras(@Nonnull Minecraft minecraft)
    {
        animatedProcess.draw(minecraft, 40, 23);
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
        return RecipeCategories.MANA_INFUSER;
    }
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @Nonnull ManaInfuserRecipe recipeWrapper, @Nonnull IIngredients ingredients)
    {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        stacks.init(input, true, 21, 42);
        stacks.init(output, false, 76, 23);
        stacks.set(ingredients);
    }
}
