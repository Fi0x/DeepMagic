package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.DeepMagic;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DeepMagic.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ITEMS = CREATIVE_MODE_TABS.register("deepmagic_items_tab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.DEEP_CRYSTAL.get()))
            .title(Component.translatable("itemGroup.deepmagic_items_tab"))
            .displayItems((pParameters, pOutput) -> {
                pOutput.accept(ModItems.DEEP_CRYSTAL.get());
            }).build());
}
