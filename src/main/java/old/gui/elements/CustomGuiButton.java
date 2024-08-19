package com.fi0x.deepmagic.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

public class CustomGuiButton extends GuiButton
{
    private final ResourceLocation TEXTURE;
    private final int textureX;
    private final int textureY;

    public CustomGuiButton(int buttonId, int x, int y, ResourceLocation texture, int u, int v)
    {
        super(buttonId, x, y, 11, 7, "");
        TEXTURE = texture;
        textureX = u;
        textureY = v;
    }
    @Override
    public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if(visible)
        {
            boolean overButton = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
            GL11.glColor4f(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(TEXTURE);

            int yOff = overButton ? 7 : 0;
            drawTexturedModalRect(x, y, textureX, textureY + yOff, width, height);
        }
    }
}
