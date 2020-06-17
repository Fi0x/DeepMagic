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
    private GuiButton buttonAddManaRegen;
    private GuiButton buttonAddManaEfficiency;
    private GuiButton buttonAddMaxHP;
    private GuiButton buttonAddHealthRegen;

    private GuiLabel labelSkillpoints;
    private GuiLabel labelMaxMana;
    private GuiLabel labelManaRegen;
    private GuiLabel labelManaEfficiency;
    private GuiLabel labelMaxHP;
    private GuiLabel labelHealthRegen;

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
        buttonAddMaxMana = new GuiButton(1, guiX + 160, guiY + 40, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddMaxMana);
        buttonAddManaRegen = new GuiButton(2, guiX + 160, guiY + 60, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddManaRegen);
        buttonAddManaEfficiency = new GuiButton(3, guiX + 160, guiY + 80, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddManaEfficiency);
        buttonAddMaxHP = new GuiButton(4, guiX + 160, guiY + 100, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddMaxHP);
        buttonAddHealthRegen = new GuiButton(5, guiX + 160, guiY + 120, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddHealthRegen);

        labelSkillpoints = new GuiLabel(this.fontRenderer, 100, guiX + 5, guiY + 5, 100, 20, 0);
        labelSkillpoints.addLine("Skillpoints: ");
        labelList.add(labelSkillpoints);
        labelMaxMana = new GuiLabel(this.fontRenderer, 101, guiX + 5, guiY + 40, 150, 20, 0);
        labelMaxMana.addLine("Mana Capacity");
        labelList.add(labelMaxMana);
        labelManaRegen = new GuiLabel(this.fontRenderer, 102, guiX + 5, guiY + 60, 150, 20, 0);
        labelManaRegen.addLine("Mana Regeneration");
        labelList.add(labelManaRegen);
        labelManaEfficiency = new GuiLabel(this.fontRenderer, 103, guiX + 5, guiY + 80, 150, 20, 0);
        labelManaEfficiency.addLine("Mana Efficiency");
        labelList.add(labelManaEfficiency);
        labelMaxHP = new GuiLabel(this.fontRenderer, 104, guiX + 5, guiY + 100, 150, 20, 0);
        labelMaxHP.addLine("Health Points");
        labelList.add(labelMaxHP);
        labelHealthRegen = new GuiLabel(this.fontRenderer, 105, guiX + 5, guiY + 120, 150, 20, 0);
        labelHealthRegen.addLine("Health Regeneration");
        labelList.add(labelHealthRegen);
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