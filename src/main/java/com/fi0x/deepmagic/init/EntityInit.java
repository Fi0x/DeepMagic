package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.entities.EntityInsanityCow;
import com.fi0x.deepmagic.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit
{
	public static void registerEntities()
	{
		registerEntity("insanity_cow", EntityInsanityCow.class, Reference.ENTITY_INSANITY_COW, 50, 2630189, 8991621);
	}
	
	private static void registerEntity(String name, Class<? extends Entity> entityClass, int id, int trackingRange, int eggColor1, int eggColor2)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entityClass, name, id, Main.instance, trackingRange, 1, true, eggColor1, eggColor2);
	}
}