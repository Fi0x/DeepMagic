package com.fi0x.deepmagic.proxy;

import com.fi0x.deepmagic.gui.GuiSkilltree;
import com.fi0x.deepmagic.mana.player.GuiManaRenderOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Objects;

public class ClientProxy extends CommonProxy
{
	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), id));
	}

	@Override
	public void openSkilltreeGui()
	{
		Minecraft.getMinecraft().addScheduledTask( () -> Minecraft.getMinecraft().displayGuiScreen(new GuiSkilltree()));
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(GuiManaRenderOverlay.instance);
	}
}