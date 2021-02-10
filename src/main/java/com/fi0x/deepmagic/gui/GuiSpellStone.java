package com.fi0x.deepmagic.gui;

import com.fi0x.deepmagic.blocks.containers.ContainerSpellStone;
import com.fi0x.deepmagic.blocks.mana.tile.TileEntitySpellStone;
import com.fi0x.deepmagic.util.Reference;
import net.minecraft.client.gui.GuiButton;
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

    private GuiButton btnBind;
    private GuiButton btnAddPart;
    private GuiButton btnClearParts;

    private String currentPart;
    private String part1;
    private String part2;
    private String part3;
    private String part4;

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
        currentPart = "";
        part1 = "";
        part2 = "";
        part3 = "";
        part4 = "";

        btnBind = new GuiButton(nextID(), guiLeft + 125, guiTop + 104, 40, 20, "Bind");
        buttonList.add(btnBind);
        btnAddPart = new GuiButton(nextID(), guiLeft + 104, guiTop + 18, 30, 20, "Add");
        buttonList.add(btnAddPart);
        btnClearParts = new GuiButton(nextID(), guiLeft + 11, guiTop + 104, 75, 20, "Clear Parts");
        buttonList.add(btnClearParts);
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

        fontRenderer.drawString(currentPart, 13, 39, 3289650);
        fontRenderer.drawString(part1, 13, 52, 3289650);
        fontRenderer.drawString(part2, 13, 65, 3289650);
        fontRenderer.drawString(part3, 13, 78, 3289650);
        fontRenderer.drawString(part4, 13, 91, 3289650);
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

        if(button == btnBind)
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
    @Override
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
    }
    private int nextID()
    {
        return elementID++;
    }
}