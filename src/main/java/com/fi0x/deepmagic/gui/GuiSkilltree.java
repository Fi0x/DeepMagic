package com.fi0x.deepmagic.gui;

import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import com.fi0x.deepmagic.network.PacketGetSkill;
import com.fi0x.deepmagic.util.Reference;
import com.fi0x.deepmagic.util.handlers.PacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
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
    private int elementID = 1;

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

    private GuiTextField txtSkillPoints;
    private GuiTextField txtMaxMana;
    private GuiTextField txtManaRegen;
    private GuiTextField txtManaEfficiency;
    private GuiTextField txtMaxHP;
    private GuiTextField txtHealthRegen;
    private GuiTextField txtSpellTier;
    private GuiTextField txtCastSkill;

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

        buttonExit = new GuiButton(nextID(), width/2 -20, guiY + backgroundHeight-30, 40, 20, I18n.format("Exit"));
        buttonList.add(buttonExit);
        buttonAddMaxMana = new GuiButton(nextID(), guiX + 200, guiY + 5, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddMaxMana);
        buttonAddManaRegenRate = new GuiButton(nextID(), guiX + 200, guiY + 25, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddManaRegenRate);
        buttonAddManaEfficiency = new GuiButton(nextID(), guiX + 200, guiY + 45, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddManaEfficiency);
        buttonAddMaxHP = new GuiButton(nextID(), guiX + 200, guiY + 65, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddMaxHP);
        buttonAddHPRegen = new GuiButton(nextID(), guiX + 200, guiY + 85, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddHPRegen);
        buttonAddSpellTier = new GuiButton(nextID(), guiX + 200, guiY + 105, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddSpellTier);
        buttonAddSpellCastSkill = new GuiButton(nextID(), guiX + 200, guiY + 125, 20, 20, I18n.format("+"));
        buttonList.add(buttonAddSpellCastSkill);

        updateScreen();

        labelSkillPoint = new GuiLabel(this.fontRenderer, nextID(), guiX + backgroundWidth - 60, guiY + 5, 55, 20, 0);
        labelSkillPoint.addLine("Skillpoints");
        labelList.add(labelSkillPoint);

        labelMaxMana = new GuiLabel(this.fontRenderer, nextID(), guiX + 5, guiY + 5, 100, 20, 0);
        labelMaxMana.addLine("Mana Capacity");
        labelList.add(labelMaxMana);
        labelManaRegen = new GuiLabel(this.fontRenderer, nextID(), guiX + 5, guiY + 25, 100, 20, 0);
        labelManaRegen.addLine("Mana Regeneration");
        labelList.add(labelManaRegen);
        labelManaEfficiency = new GuiLabel(this.fontRenderer, nextID(), guiX + 5, guiY + 45, 100, 20, 0);
        labelManaEfficiency.addLine("Mana Efficiency");
        labelList.add(labelManaEfficiency);
        labelMaxHP = new GuiLabel(this.fontRenderer, nextID(), guiX + 5, guiY + 65, 100, 20, 0);
        labelMaxHP.addLine("Health Points");
        labelList.add(labelMaxHP);
        labelHealthRegen = new GuiLabel(this.fontRenderer, nextID(), guiX + 5, guiY + 85, 100, 20, 0);
        labelHealthRegen.addLine("Health Regeneration");
        labelList.add(labelHealthRegen);
        labelSpellTier = new GuiLabel(this.fontRenderer, nextID(), guiX + 5, guiY + 105, 100, 20, 0);
        labelSpellTier.addLine("Spell-Tier");
        labelList.add(labelSpellTier);
        labelCastSkill = new GuiLabel(this.fontRenderer, nextID(), guiX + 5, guiY + 125, 100, 20, 0);
        labelCastSkill.addLine("Spell-Cast Skill");
        labelList.add(labelCastSkill);

        txtSkillPoints = new GuiTextField(nextID(), this.fontRenderer, guiX + backgroundWidth -90, guiY + 7, 25, 16);

        txtMaxMana = new GuiTextField(nextID(), this.fontRenderer, guiX + 130, guiY + 7, 60, 16);
        txtManaRegen = new GuiTextField(nextID(), this.fontRenderer, guiX + 130, guiY + 27, 60, 16);
        txtManaEfficiency = new GuiTextField(nextID(), this.fontRenderer, guiX + 130, guiY + 47, 60, 16);
        txtMaxHP = new GuiTextField(nextID(), this.fontRenderer, guiX + 130, guiY + 67, 60, 16);
        txtHealthRegen = new GuiTextField(nextID(), this.fontRenderer, guiX + 130, guiY + 87, 60, 16);
        txtSpellTier = new GuiTextField(nextID(), this.fontRenderer, guiX + 130, guiY + 107, 60, 16);
        txtCastSkill = new GuiTextField(nextID(), this.fontRenderer, guiX + 130, guiY + 127, 60, 16);

        updateTextBoxes();
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
            buttonAddSpellTier.visible = true;
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
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.getTextureManager().bindTexture(backgroundTexture);
        drawTexturedModalRect(guiX, guiY, 0, 0, backgroundWidth, backgroundHeight);
        updateStats();
        updateTextBoxes();
        drawTextBoxes();

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
        updateStats();
        updateTextBoxes();
    }
    @Override
    public void onGuiClosed(){ }
    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    private void updateStats()
    {
        valueSkillPoint = playerMana.getSkillpoints();
        valueMaxMana = playerMana.getMaxMana();
        valueManaRegen = playerMana.getManaRegenRate();
        valueManaEfficiency = playerMana.getManaEfficiency();
        valueMaxHP = (playerMana.addedHP+20);
        valueHPRegen = playerMana.hpRegeneration;
        valueSpellTier = playerMana.getSpellTier();
        valueCastingSkill = playerMana.spellCastSkill;
    }
    private void updateTextBoxes()
    {
        txtSkillPoints.setText("" + valueSkillPoint);
        txtMaxMana.setText("" + (int) valueMaxMana);
        txtManaRegen.setText("" + (int) valueManaRegen);
        txtManaEfficiency.setText("" + (int) valueManaEfficiency);
        txtMaxHP.setText("" + valueMaxHP);
        txtHealthRegen.setText("" + valueHPRegen);
        txtSpellTier.setText("" + valueSpellTier);
        txtCastSkill.setText("" + valueCastingSkill);
    }
    private void drawTextBoxes()
    {
        txtSkillPoints.drawTextBox();
        txtMaxMana.drawTextBox();
        txtManaRegen.drawTextBox();
        txtManaEfficiency.drawTextBox();
        txtMaxHP.drawTextBox();
        txtHealthRegen.drawTextBox();
        txtSpellTier.drawTextBox();
        txtCastSkill.drawTextBox();
    }
    private int nextID()
    {
        return elementID++;
    }
}