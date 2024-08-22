package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.DeepMagic;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DeepMagic.MOD_ID);

    public static final RegistryObject<Item> DEEP_CRYSTAL = ITEMS.register("deep_crystal", () -> new Item(new Item.Properties()));
}
