package com.fi0x.deepmagic.util.compat.jei.manainfuser;

import com.fi0x.deepmagic.util.Reference;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractManaInfuserRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T>
{
    protected static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/mana_infuser.png");

    protected static final int input = 0;
    protected static final int output = 1;

    protected final IDrawableAnimated animatedProcess;
    protected final IDrawableAnimated animatedMana;

    public AbstractManaInfuserRecipeCategory(IGuiHelper helper)
    {
        IDrawableStatic staticProcess = helper.createDrawable(TEXTURES, 176, 50, 22, 15);
        animatedProcess = helper.createAnimatedDrawable(staticProcess, 200, IDrawableAnimated.StartDirection.LEFT, false);

        IDrawableStatic staticMana = helper.createDrawable(TEXTURES, 176, 0, 16, 50);
        animatedMana = helper.createAnimatedDrawable(staticMana, 200, IDrawableAnimated.StartDirection.TOP, true);
    }
}
