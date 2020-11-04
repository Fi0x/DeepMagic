package com.fi0x.deepmagic.gui;

import com.fi0x.deepmagic.blocks.containers.ContainerManaGenerator;
import com.fi0x.deepmagic.blocks.tileentity.TileEntityManaGenerator;
import com.fi0x.deepmagic.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiManaGenerator extends GuiContainer
{
    private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/mana_generator.png");
    private final InventoryPlayer player;
    private final TileEntityManaGenerator te;

    public GuiManaGenerator(InventoryPlayer player, TileEntityManaGenerator tileentity)
    {
        super(new ContainerManaGenerator(player, tileentity));
        this.player = player;
        te = tileentity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String tileName = te.getDisplayName().getUnformattedText();
        fontRenderer.drawString(tileName, xSize / 2 - fontRenderer.getStringWidth(tileName) / 2, 10, 4210752);
        fontRenderer.drawString(player.getDisplayName().getUnformattedText(), 122, ySize - 94, 4210752);
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(TEXTURES);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}