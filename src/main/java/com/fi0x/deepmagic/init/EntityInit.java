package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.entities.EntityInsanityCow;
import com.fi0x.deepmagic.entities.EntitySpikySlime;
import com.fi0x.deepmagic.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit
{
    public static void registerEntities()
    {
        registerEntity("insanity_cow", EntityInsanityCow.class, Reference.ENTITY_INSANITY_COW, 50, 12925456, 12960970);
        registerEntity("spiky_slime", EntitySpikySlime.class, Reference.ENTITY_SPIKY_SLIME, 50, 7864320, 2818048);
    }
    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2)
    {
        EntityRegistry.registerModEntity(new ResourceLocation("deepmagic:" + name), entity, name, id, Main.instance, range, 1, true, color1, color2);
    }
}