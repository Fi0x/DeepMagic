package com.fi0x.deepmagic.gui;

import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import com.fi0x.deepmagic.network.PacketGetSkill;
import com.fi0x.deepmagic.network.PacketHandler;
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
    PlayerMana playerMana;
    String playerName;

    private final int backgroundHeight = 256;
    private final int backgroundWidth = 512;
    private int guiX;
    private int guiY;
    private static final ResourceLocation  backgroundTexture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/skilltree_background.png");
    private GuiButton buttonExit;
    private GuiButton buttonAddMaxMana;
    private GuiButton buttonAddManaRegenRate;
    private GuiButton buttonAddManaEfficiency;
    private GuiButton buttonAddMaxHP;
    private GuiButton buttonAddHPRegen;
    private GuiButton buttonAddSpellTier;
    private GuiButton buttonAddSpellCastSkill;

    private GuiLabel labelSkillPoint;
    private GuiLabel labelMaxMana;
    private GuiLabel labelManaRegen;
    private GuiLabel labelManaEfficiency;
    private GuiLabel labelMaxHP;
    private GuiLabel labelHealthRegen;
    private GuiLabel labelSpellTier;
    private GuiLabel labelCastSkill;

    private int valueSkillPoint;
    private double valueMaxMana;
    private double valueManaRegen;
    private double valueManaEfficiency;
    private int valueMaxHP;
    private int valueHPRegen;
    private int valueSpellTier;
    private int valueCastingSkill;

    public GuiSkilltree(EntityPlayer player)
    {
        this.playerMana = player.getCapability(PlayerProperties.PLAYER_MANA, null);
        this.playerName = player.getName();
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
        buttonAddMaxMana = new GuiButton(1, guiX + 160, guiY + 5, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddMaxMana);
        buttonAddManaRegenRate = new GuiButton(2, guiX + 160, guiY + 25, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddManaRegenRate);
        buttonAddManaEfficiency = new GuiButton(3, guiX + 160, guiY + 45, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddManaEfficiency);
        buttonAddMaxHP = new GuiButton(4, guiX + 160, guiY + 65, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddMaxHP);
        buttonAddHPRegen = new GuiButton(5, guiX + 160, guiY + 85, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddHPRegen);
        buttonAddSpellTier = new GuiButton(6, guiX + 160, guiY + 105, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddSpellTier);
        buttonAddSpellCastSkill = new GuiButton(6, guiX + 160, guiY + 125, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddSpellCastSkill);

        updateScreen();

        labelSkillPoint = new GuiLabel(this.fontRenderer, 100, guiX + backgroundWidth / 2 - 30, guiY + backgroundHeight - 60, 150, 20, 0);
        labelSkillPoint.addLine("Skillpoints: " + valueSkillPoint);
        labelList.add(labelSkillPoint);
        labelMaxMana = new GuiLabel(this.fontRenderer, 101, guiX + 5, guiY + 5, 100, 20, 0);
        labelMaxMana.addLine("Mana Capacity: " + (int) valueMaxMana);
        labelList.add(labelMaxMana);
        labelManaRegen = new GuiLabel(this.fontRenderer, 102, guiX + 5, guiY + 25, 100, 20, 0);
        labelManaRegen.addLine("Mana Regeneration: " + (int) valueManaRegen);
        labelList.add(labelManaRegen);
        labelManaEfficiency = new GuiLabel(this.fontRenderer, 103, guiX + 5, guiY + 45, 100, 20, 0);
        labelManaEfficiency.addLine("Mana Efficiency: " + (int) valueManaEfficiency);
        labelList.add(labelManaEfficiency);
        labelMaxHP = new GuiLabel(this.fontRenderer, 104, guiX + 5, guiY + 65, 100, 20, 0);
        labelMaxHP.addLine("Health Points: " + valueMaxHP);
        labelList.add(labelMaxHP);
        labelHealthRegen = new GuiLabel(this.fontRenderer, 105, guiX + 5, guiY + 85, 100, 20, 0);
        labelHealthRegen.addLine("Health Regeneration: " + valueHPRegen);
        labelList.add(labelHealthRegen);
        labelSpellTier = new GuiLabel(this.fontRenderer, 106, guiX + 5, guiY + 105, 100, 20, 0);
        labelSpellTier.addLine("Spell-Tier: " + valueSpellTier);
        labelList.add(labelSpellTier);
        labelCastSkill = new GuiLabel(this.fontRenderer, 107, guiX + 5, guiY + 125, 100, 20, 0);
        labelCastSkill.addLine("Spell-Cast Skill: " + valueCastingSkill);
        labelList.add(labelCastSkill);
    }

    @Override
    public void updateScreen()
    {
        if(playerMana.getSkillpoints() > 0)
        {
            buttonAddMaxMana.visible = true;
            buttonAddManaRegenRate.visible = true;
            buttonAddManaEfficiency.visible = true;
            buttonAddMaxHP.visible = true;
            buttonAddHPRegen.visible = true;
            if(playerMana.getSpellTier() < 10) buttonAddSpellTier.visible = true;
            else buttonAddSpellTier.visible = false;
            buttonAddSpellCastSkill.visible = true;
        } else
        {
            buttonAddMaxMana.visible = false;
            buttonAddManaRegenRate.visible = false;
            buttonAddManaEfficiency.visible = false;
            buttonAddMaxHP.visible = false;
            buttonAddHPRegen.visible = false;
            buttonAddSpellTier.visible = false;
            buttonAddSpellCastSkill.visible = false;
        }

        valueSkillPoint = playerMana.getSkillpoints();
        valueMaxMana = playerMana.getMaxMana();
        valueManaRegen = playerMana.getManaRegenRate();
        valueManaEfficiency = playerMana.getManaEfficiency();
        valueMaxHP = (playerMana.addedHP+20);
        valueHPRegen = playerMana.hpRegeneration;
        valueSpellTier = playerMana.getSpellTier();
        valueCastingSkill = playerMana.spellCastSkill;
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
        if(button == buttonExit) mc.displayGuiScreen(null);
        else
        {
            if(button == buttonAddMaxMana) playerMana.maxManaMultiplier++;
            else if(button == buttonAddManaRegenRate) playerMana.setManaRegenRate(playerMana.getManaRegenRate() + 1);
            else if(button == buttonAddManaEfficiency) playerMana.setManaEfficiency(playerMana.getManaEfficiency() + 1);
            else if(button == buttonAddMaxHP) playerMana.addedHP++;
            else if(button == buttonAddHPRegen) playerMana.hpRegeneration++;
            else if(button == buttonAddSpellTier) playerMana.addSpellTier();
            else if(button == buttonAddSpellCastSkill) playerMana.spellCastSkill++;
            playerMana.removeSkillpoint();
        }
        PacketHandler.INSTANCE.sendToServer(new PacketGetSkill(playerName, playerMana.getMaxMana(), playerMana.getSkillXP(), playerMana.getSkillpoints(), playerMana.getManaRegenRate(), playerMana.getManaEfficiency(), playerMana.addedHP, playerMana.hpRegeneration, playerMana.getSpellTier(), playerMana.spellCastSkill));
    }

    @Override
    public void onGuiClosed(){ }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}