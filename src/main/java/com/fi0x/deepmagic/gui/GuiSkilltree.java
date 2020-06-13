package com.fi0x.deepmagic.gui;

import com.fi0x.deepmagic.util.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

public class GuiSkilltree extends GuiScreen
{
    private final int backgroundHeight = 256;
    private final int backgroundWidth = 256;
    private static final ResourceLocation  backgroundTexture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/skilltree_background.png");
    private GuiButton buttonExit;

    public GuiSkilltree()
    {
    }

    @Override
    public void initGui()
    {
        buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        buttonExit = new GuiButton(0, width/2 -20, (height-backgroundHeight)/2 + backgroundHeight-30, 40, 20, I18n.format("Exit", new Object[0]));
        buttonList.add(buttonExit);
    }

    @Override
    public void updateScreen()
    {
        buttonExit.visible = true;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.getTextureManager().bindTexture(backgroundTexture);
        int offsetLeft = (width - backgroundWidth) / 2;
        int offsetTop = (height - backgroundHeight) / 2;
        drawTexturedModalRect(offsetLeft, offsetTop, 0, 0, backgroundWidth, backgroundHeight);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick){ }

    @Override
    protected void actionPerformed(@Nonnull GuiButton button)
    {
        if(button == buttonExit)
        {
            mc.displayGuiScreen((GuiScreen) null);
        }
        //TODO: send server packet if button is a skillpoint adder to change values on serverside
    }

    @Override
    public void onGuiClosed(){ }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}