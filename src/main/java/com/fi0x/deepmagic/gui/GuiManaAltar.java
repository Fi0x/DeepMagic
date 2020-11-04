package com.fi0x.deepmagic.gui;

import com.fi0x.deepmagic.blocks.containers.ContainerManaGenerator;
import com.fi0x.deepmagic.blocks.tileentity.TileEntityManaGenerator;
import com.fi0x.deepmagic.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiManaAltar extends GuiContainer
{
    private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/mana_generator.png");
    private final InventoryPlayer player;
    private final TileEntityManaGenerator te;

    public GuiManaAltar(InventoryPlayer player, TileEntityManaGenerator tileentity)
    {
        super(new ContainerManaGenerator(player, tileentity));
        this.player = player;
        te = tileentity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {

    }
}