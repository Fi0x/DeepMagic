package com.fi0x.deepmagic.gui;

import com.fi0x.deepmagic.blocks.containers.ContainerSpellStone;
import com.fi0x.deepmagic.blocks.mana.tile.TileEntitySpellStone;
import com.fi0x.deepmagic.gui.elements.CustomGuiButton;
import com.fi0x.deepmagic.network.PacketInformGuiChange;
import com.fi0x.deepmagic.util.Reference;
import com.fi0x.deepmagic.util.handlers.PacketHandler;
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
    private CustomGuiButton btnPrevParts;
    private CustomGuiButton btnNextParts;

    private int firstRowIdx;

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
        firstRowIdx = 0;

        btnBind = new GuiButton(nextID(), guiLeft + 125, guiTop + 104, 40, 20, "Bind");
        buttonList.add(btnBind);
        btnAddPart = new GuiButton(nextID(), guiLeft + 104, guiTop + 18, 30, 20, "Add");
        buttonList.add(btnAddPart);
        btnClearParts = new GuiButton(nextID(), guiLeft + 11, guiTop + 104, 75, 20, "Clear Parts");
        buttonList.add(btnClearParts);

        btnPrevParts = new CustomGuiButton(nextID(), guiLeft + 125, guiTop + 62, TEXTURES, 192, 0);
        buttonList.add(btnPrevParts);
        btnPrevParts.visible = firstRowIdx > 0;
        btnNextParts = new CustomGuiButton(nextID(), guiLeft + 125, guiTop + 82, TEXTURES, 203, 0);
        buttonList.add(btnNextParts);
        btnNextParts.visible = firstRowIdx + 4 < te.getPartNames().size();
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

        fontRenderer.drawString(te.getCurrentPartName(), 13, 39, 3289650);
        if(te.getPartNames().size() > firstRowIdx) fontRenderer.drawString(te.getPartNames().get(firstRowIdx), 13, 52, 3289650);
        if(te.getPartNames().size() > firstRowIdx + 1) fontRenderer.drawString(te.getPartNames().get(firstRowIdx + 1), 13, 65, 3289650);
        if(te.getPartNames().size() > firstRowIdx + 2) fontRenderer.drawString(te.getPartNames().get(firstRowIdx + 2), 13, 78, 3289650);
        if(te.getPartNames().size() > firstRowIdx + 3) fontRenderer.drawString(te.getPartNames().get(firstRowIdx + 3), 13, 91, 3289650);
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(TEXTURES);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int p = getProgressScaled(46);
        drawTexturedModalRect(guiLeft + 148, guiTop + 37, 176, 0, 16, p);

        btnBind.visible = p == 0 && te.getPartCount() > 0;
        btnAddPart.visible = p == 0;
        btnClearParts.visible = p == 0;

        btnPrevParts.visible = firstRowIdx > 0;
        btnNextParts.visible = firstRowIdx + 4 < te.getPartNames().size();
    }
    @Override
    protected void actionPerformed(@Nonnull GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if(button == btnBind)
        {
            PacketHandler.INSTANCE.sendToServer(new PacketInformGuiChange(te.getWorld().provider.getDimension(), te.getPos(), 1));
        } else if(button == btnAddPart)
        {
            PacketHandler.INSTANCE.sendToServer(new PacketInformGuiChange(te.getWorld().provider.getDimension(), te.getPos(), 2));
        } else if(button == btnClearParts)
        {
            PacketHandler.INSTANCE.sendToServer(new PacketInformGuiChange(te.getWorld().provider.getDimension(), te.getPos(), 3));
            firstRowIdx = 0;
        } else if(button == btnPrevParts)
        {
            if(firstRowIdx > 0) firstRowIdx--;
        } else if(button == btnNextParts)
        {
            if(firstRowIdx + 4 < te.getPartNames().size()) firstRowIdx++;
        }
    }

    private int getProgressScaled(int pixels)
    {
        int i = te.getField(0);
        if(i <= 0) return 0;
        return (i - te.getField(1)) * pixels / i;
    }
    private int nextID()
    {
        return elementID++;
    }
}