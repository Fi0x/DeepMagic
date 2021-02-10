package com.fi0x.deepmagic.gui;

import com.fi0x.deepmagic.blocks.containers.ContainerSpellStone;
import com.fi0x.deepmagic.blocks.mana.tile.TileEntitySpellStone;
import com.fi0x.deepmagic.util.Reference;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Objects;

public class GuiSpellStone extends GuiContainer
{
    private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/spell_stone.png");
    private final InventoryPlayer player;
    private final TileEntitySpellStone te;

    private int elementID = 1;

    private GuiButton btnStart;
    private GuiButton btnAddPart;
    private GuiButton btnClearParts;

    private GuiLabel lblPart1;
    private GuiLabel lblPart2;
    private GuiLabel lblPart3;
    private GuiLabel lblPart4;

    public GuiSpellStone(InventoryPlayer player, TileEntitySpellStone tileentity)
    {
        super(new ContainerSpellStone(player, tileentity));
        ySize = 222;
        this.player = player;
        te = tileentity;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.clear();
        labelList.clear();

        btnStart = new GuiButton(nextID(), guiLeft + 105, guiTop + 104, 60, 20, "Bind");
        buttonList.add(btnStart);
        btnAddPart = new GuiButton(nextID(), guiLeft + 104, guiTop + 18, 30, 20, "Add");
        buttonList.add(btnAddPart);
        btnClearParts = new GuiButton(nextID(), guiLeft + 11, guiTop + 104, 60, 20, "Clear");
        buttonList.add(btnClearParts);

        FontRenderer labelFont = fontRenderer;
        labelFont.FONT_HEIGHT = 12;
        lblPart1 = new GuiLabel(labelFont, nextID(), guiLeft + 11, guiTop + 50, 114, 12, 0);
        lblPart1.addLine("First Part");
        labelList.add(lblPart1);
        lblPart2 = new GuiLabel(labelFont, nextID(), guiLeft + 11, guiTop + 63, 114, 12, 0);
        lblPart2.addLine("Second Part");
        labelList.add(lblPart2);
        lblPart3 = new GuiLabel(labelFont, nextID(), guiLeft + 11, guiTop + 76, 114, 12, 0);
        lblPart3.addLine("Third Part");
        labelList.add(lblPart3);
        lblPart4 = new GuiLabel(labelFont, nextID(), guiLeft + 11, guiTop + 89, 114, 12, 0);
        lblPart4.addLine("Fourth Part");
        labelList.add(lblPart4);
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String tileName = Objects.requireNonNull(te.getDisplayName()).getUnformattedText();
        fontRenderer.drawString(tileName, xSize / 2 - fontRenderer.getStringWidth(tileName) / 2, 6, 4210752);
        fontRenderer.drawString(player.getDisplayName().getUnformattedText(), 7, ySize - 94, 4210752);
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(TEXTURES);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
    @Override
    protected void actionPerformed(@Nonnull GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if(button == btnStart)
        {
            //TODO: Add spell parts to spell
        } else if(button == btnAddPart)
        {
            //TODO: Get part and add to list
        } else if(button == btnClearParts)
        {
            //TODO: Remove all parts and drop items
        }
    }
    private int nextID()
    {
        return elementID++;
    }
}