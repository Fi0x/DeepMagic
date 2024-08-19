package com.fi0x.deepmagic.gui;

import com.fi0x.deepmagic.blocks.containers.ContainerMinerStash;
import com.fi0x.deepmagic.blocks.tileentity.TileEntityMinerStash;
import com.fi0x.deepmagic.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiMinerStash extends GuiContainer
{
    private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/miner_stash.png");
    private final InventoryPlayer player;
    private final TileEntityMinerStash te;

    public GuiMinerStash(InventoryPlayer player, TileEntityMinerStash tileentity)
    {
        super(new ContainerMinerStash(player, tileentity));
        this.player = player;
        te = tileentity;
        xSize = 256;
        ySize = 256;
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
        String tileName = te.getDisplayName().getUnformattedText();
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
}