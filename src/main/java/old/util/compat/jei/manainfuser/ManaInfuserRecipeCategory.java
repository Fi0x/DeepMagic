package com.fi0x.deepmagic.util.compat.jei.manainfuser;

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

public class ManaInfuserRecipeCategory extends AbstractManaInfuserRecipeCategory<ManaInfuserRecipe>
{
    private final IDrawable background;
    private final String name;
    private final IDrawable icon;

    public ManaInfuserRecipeCategory(IGuiHelper helper)
    {
        super(helper);
        background = helper.createDrawable(TEXTURES, 4, 8, 168, 66);
        name = "Mana Infuser";
        icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.MANA_INFUSER));
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
        animatedProcess.draw(minecraft, 43, 17);
        animatedMana.draw(minecraft, 137, 8);
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
        stacks.init(input, true, 16, 16);
        stacks.init(output, false, 81, 16);
        stacks.set(ingredients);
    }
}
