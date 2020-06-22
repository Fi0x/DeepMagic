package com.fi0x.deepmagic.gui;

import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import com.fi0x.deepmagic.util.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

public class GuiSkilltree extends GuiScreen
{
    EntityPlayer player;
    PlayerMana playerMana;

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

    public GuiSkilltree(EntityPlayer player)
    {
        this.player = player;
        this.playerMana = player.getCapability(PlayerProperties.PLAYER_MANA, null);
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
        buttonAddMaxMana = new GuiButton(1, guiX + backgroundWidth - 40, guiY + 40, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddMaxMana);
        buttonAddManaRegen = new GuiButton(2, guiX + backgroundWidth - 40, guiY + 60, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddManaRegen);
        buttonAddManaEfficiency = new GuiButton(3, guiX + backgroundWidth - 40, guiY + 80, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddManaEfficiency);
        buttonAddMaxHP = new GuiButton(4, guiX + backgroundWidth - 40, guiY + 100, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddMaxHP);
        buttonAddHealthRegen = new GuiButton(5, guiX + backgroundWidth - 40, guiY + 120, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddHealthRegen);

        labelSkillpoints = new GuiLabel(this.fontRenderer, 100, guiX + 5, guiY + 5, 100, 20, 0);
        labelSkillpoints.addLine("Skillpoints: " + playerMana.getSkillpoints());
        labelList.add(labelSkillpoints);
        labelMaxMana = new GuiLabel(this.fontRenderer, 101, guiX + 5, guiY + 40, 150, 20, 0);
        labelMaxMana.addLine("Mana Capacity: " + playerMana.getMaxMana());
        labelList.add(labelMaxMana);
        labelManaRegen = new GuiLabel(this.fontRenderer, 102, guiX + 5, guiY + 60, 150, 20, 0);
        labelManaRegen.addLine("Mana Regeneration: " + playerMana.getManaRegenRate());
        labelList.add(labelManaRegen);
        labelManaEfficiency = new GuiLabel(this.fontRenderer, 103, guiX + 5, guiY + 80, 150, 20, 0);
        labelManaEfficiency.addLine("Mana Efficiency: " + playerMana.getManaEfficiency());
        labelList.add(labelManaEfficiency);
        labelMaxHP = new GuiLabel(this.fontRenderer, 104, guiX + 5, guiY + 100, 150, 20, 0);
        labelMaxHP.addLine("Health Points: " + (playerMana.addedHP+20));
        labelList.add(labelMaxHP);
        labelHealthRegen = new GuiLabel(this.fontRenderer, 105, guiX + 5, guiY + 120, 150, 20, 0);
        labelHealthRegen.addLine("Health Regeneration: " + playerMana.hpRegeneration);
        labelList.add(labelHealthRegen);
    }

    @Override
    public void updateScreen()
    {
        if(playerMana.getSkillpoints() > 0)
        {
            buttonAddMaxMana.visible = true;
            buttonAddManaRegen.visible = true;
            buttonAddManaEfficiency.visible = true;
            buttonAddMaxHP.visible = true;
            buttonAddHealthRegen.visible = true;
        } else
        {
            buttonAddMaxMana.visible = false;
            buttonAddManaRegen.visible = false;
            buttonAddManaEfficiency.visible = false;
            buttonAddMaxHP.visible = false;
            buttonAddHealthRegen.visible = false;
        }
        //TODO: update stats
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
            //TODO: Sync Client with server
        } else if(button == buttonAddMaxMana)
        {
            if(playerMana.removeSkillpoint()) playerMana.setMaxMana(playerMana.getMaxMana() + 1);
        } else if(button == buttonAddManaRegen)
        {
            if(playerMana.removeSkillpoint()) playerMana.setManaRegenRate(playerMana.getManaRegenRate() + 1);
        } else if(button == buttonAddManaEfficiency)
        {
            if(playerMana.removeSkillpoint()) playerMana.setManaEfficiency(playerMana.getManaEfficiency() + 1);
        } else if(button == buttonAddMaxHP)
        {
            if(playerMana.removeSkillpoint()) playerMana.addedHP++;
        } else if(button == buttonAddHealthRegen)
        {
            if(playerMana.removeSkillpoint()) playerMana.hpRegeneration++;
        }
    }

    @Override
    public void onGuiClosed(){ }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}