package com.fi0x.deepmagic.gui;

import com.fi0x.deepmagic.blocks.containers.ContainerSpellStone;
import com.fi0x.deepmagic.blocks.mana.tile.TileEntitySpellStone;
import com.fi0x.deepmagic.gui.elements.CustomGuiButton;
import com.fi0x.deepmagic.util.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
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

    private String newPartName;//TODO: Update as soon as items change
    private ArrayList<String> addedParts;
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
        newPartName = "";
        addedParts = new ArrayList<>();
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
        btnNextParts.visible = firstRowIdx + 4 < addedParts.size();
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

        fontRenderer.drawString(newPartName, 13, 39, 3289650);
        if(addedParts.size() > firstRowIdx) fontRenderer.drawString(addedParts.get(firstRowIdx), 13, 52, 3289650);
        if(addedParts.size() > firstRowIdx + 1) fontRenderer.drawString(addedParts.get(firstRowIdx + 1), 13, 65, 3289650);
        if(addedParts.size() > firstRowIdx + 2) fontRenderer.drawString(addedParts.get(firstRowIdx + 2), 13, 78, 3289650);
        if(addedParts.size() > firstRowIdx + 3) fontRenderer.drawString(addedParts.get(firstRowIdx + 3), 13, 91, 3289650);
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(TEXTURES);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int p = getProgressScaled(46);
        drawTexturedModalRect(guiLeft + 148, guiTop + 37, 176, 0, 16, p);

        btnBind.visible = p == 0;
    }
    @Override
    protected void actionPerformed(@Nonnull GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if(button == btnBind)
        {
            te.setField(2, 1);
            btnBind.visible = false;//TODO: Make visible again when process finished
            //TODO: Clear part-list when process finishes
        } else if(button == btnAddPart)
        {
            String partName = "";//TODO: Store part-name in variable if item combination is valid
            if(partName.equals("")) return;
            newPartName = "";
            addedParts.add(((ContainerSpellStone) inventorySlots).transformItemsToPart(partName));
        } else if(button == btnClearParts)
        {
            firstRowIdx = 0;
            addedParts.clear();
            /*
            TODO: Get a list of items that were used to create the parts (from tile entity)
             Drop all items from the list
             */
        } else if(button == btnPrevParts)
        {
            if(firstRowIdx > 0) firstRowIdx--;
        } else if(button == btnNextParts)
        {
            if(firstRowIdx + 4 < addedParts.size()) firstRowIdx++;
        }

        btnPrevParts.visible = firstRowIdx > 0;
        btnNextParts.visible = firstRowIdx + 4 < addedParts.size();
    }

    private int getProgressScaled(int pixels)
    {
        int i = te.getField(0);
        if(i == 0) return 0;
        return i - te.getField(1) * pixels / i;
    }
    private int nextID()
    {
        return elementID++;
    }
}