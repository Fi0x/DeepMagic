package com.fi0x.deepmagic.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	public void registerItemRenderer(Item item, int meta, String id) {}
	public void openSkilltreeGui(EntityPlayer player) {}

	public void preInit(FMLPreInitializationEvent event)
	{
	}
}