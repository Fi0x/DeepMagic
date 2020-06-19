package com.fi0x.deepmagic.gui;

import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import com.fi0x.deepmagic.util.IMagicItem;
import com.fi0x.deepmagic.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiManaRenderOverlay extends Gui
{
	public static GuiManaRenderOverlay instance = new GuiManaRenderOverlay();

	private final ResourceLocation MANA_BAR = new ResourceLocation(Reference.MOD_ID, "textures/gui/manabar.png");
	private final int TEXT_WIDTH = 20;
	private final int TEXT_HEIGHT = 104;
	private int positionX = 0;
	private int positionY = 0;

	public void setValues(double currentMana, double maxMana)
	{
		PlayerMana playerMana = Minecraft.getMinecraft().player.getCapability(PlayerProperties.PLAYER_MANA, null);
		assert playerMana != null;
		playerMana.setMana(currentMana);
		playerMana.setMaxMana(maxMana);
	}

	@SubscribeEvent
	public void renderManaOverlay(RenderGameOverlayEvent.Text event)
	{
		if(Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IMagicItem)
		{
			Minecraft mc = Minecraft.getMinecraft();
			PlayerMana playerMana = mc.player.getCapability(PlayerProperties.PLAYER_MANA, null);
			assert playerMana != null;
			int currentHeight = (int) playerMana.getManaPercentage();
			mc.renderEngine.bindTexture(MANA_BAR);
			drawTexturedModalRect(positionX, positionY+10, 0, 0, TEXT_WIDTH, TEXT_HEIGHT);
			drawTexturedModalRect(positionX, positionY+10+TEXT_HEIGHT-currentHeight-2, TEXT_WIDTH, 2, TEXT_WIDTH, currentHeight);
			mc.fontRenderer.drawString("" + (int) playerMana.getMaxMana(), positionX+1, positionY+1, 15658734);
			mc.fontRenderer.drawString("" + (int) playerMana.getMana(), positionX+1, positionY+TEXT_HEIGHT + 11, 15658734);
		}
	}
}