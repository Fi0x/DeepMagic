package com.fi0x.deepmagic.gui;

import com.fi0x.deepmagic.blocks.containers.ContainerManaGenerator;
import com.fi0x.deepmagic.blocks.tileentity.TileEntityManaGenerator;
import com.fi0x.deepmagic.util.Reference;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
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
        fontRenderer.drawString(tileName, xSize / 2 - fontRenderer.getStringWidth(tileName) / 2, 6, 4210752);
        fontRenderer.drawString(player.getDisplayName().getUnformattedText(), 7, ySize - 94, 4210752);
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(TEXTURES);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int p = getBurnLeftScaled(14);
        drawTexturedModalRect(guiLeft + 46,guiTop + 46 - p, 176, 14 - p, 14, p);

        int m = getStoredManaScaled(50);
        drawTexturedModalRect(guiLeft + 134, guiTop + 70 - m, 176, 14, 16, m);
    }
    private int getBurnLeftScaled(int pixels)
    {
        int i = te.getField(1);
        if(i == 0) i = 200;
        return te.getField(0) * pixels / i;
    }
    private int getStoredManaScaled(int pixels)
    {
        int i = te.getField(2);
        int j = ConfigHandler.manaGeneratorManaCapacity;
        if(j != 0 && i != 0) return i * pixels / j;
        return 0;
    }
}