package com.fi0x.deepmagic.proxy;

import com.fi0x.deepmagic.mana.player.PlayerMana;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	public void registerItemRenderer(Item item, int meta, String id) {}
	public void openSkilltreeGui(PlayerMana playerMana) {}

	public void preInit(FMLPreInitializationEvent event)
	{
	}
}