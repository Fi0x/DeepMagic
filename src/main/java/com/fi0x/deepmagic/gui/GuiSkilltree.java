package com.fi0x.deepmagic.gui;

import com.fi0x.deepmagic.util.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
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
    private int guiX;
    private int guiY;
    private static final ResourceLocation  backgroundTexture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/skilltree_background.png");
    private GuiButton buttonExit;
    private GuiButton buttonAddMaxMana;
    private GuiLabel labelSkillpoints;

    public GuiSkilltree()
    {
    }

    @Override
    public void initGui()
    {
        guiX = (width - backgroundWidth) / 2;
        guiY = (height - backgroundHeight) / 2;

        buttonList.clear();
        labelList.clear();
        Keyboard.enableRepeatEvents(true);
        buttonExit = new GuiButton(0, width/2 -20, guiY + backgroundHeight-30, 40, 20, I18n.format("Exit"));
        buttonList.add(buttonExit);
        buttonAddMaxMana = new GuiButton(1, guiX + 30, guiY + 30, 10, 10, I18n.format("+"));
        buttonList.add(buttonAddMaxMana);

        labelSkillpoints = new GuiLabel(this.fontRenderer, 2, guiX + 5, guiY + 5, 100, 20, 255);
        labelSkillpoints.addLine("Skillpoints: ");
        labelList.add(labelSkillpoints);
    }

    @Override
    public void updateScreen()
    {
        buttonExit.visible = true;
        buttonAddMaxMana.visible = true;
        labelSkillpoints.visible = true;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.getTextureManager().bindTexture(backgroundTexture);
        drawTexturedModalRect(guiX, guiY, 0, 0, backgroundWidth, backgroundHeight);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick){ }

    @Override
    protected void actionPerformed(@Nonnull GuiButton button)
    {
        if(button == buttonExit)
        {
            mc.displayGuiScreen(null);
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