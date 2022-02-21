package com.fi0x.deepmagic.proxy;

import com.fi0x.deepmagic.advancements.ModTriggers;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommonProxy
{
	public void registerItemRenderer(Item item, int meta, String id)
	{
	}
	public void openSkilltreeGui(EntityPlayer player)
	{
	}

	public void preInit(FMLPreInitializationEvent event)
	{
	}
	public void init(FMLInitializationEvent event)
	{
		Method method;

		method = ReflectionHelper.findMethod(CriteriaTriggers.class, "register", "func_192118_a", ICriterionTrigger.class);

		method.setAccessible(true);

		for (int i = 0; i < ModTriggers.TRIGGER_ARRAY.length; i++)
		{
			try
			{
				method.invoke(null, ModTriggers.TRIGGER_ARRAY[i]);
			}
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				e.printStackTrace();
			}
		}
	}
}