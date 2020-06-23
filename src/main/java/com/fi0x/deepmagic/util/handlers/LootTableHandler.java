package com.fi0x.deepmagic.util.handlers;

import com.fi0x.deepmagic.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class LootTableHandler
{
    public static final ResourceLocation HOVERING_ORB = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "hovering_orb"));
    public static final ResourceLocation ROCK_TROLL = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "rock_troll"));
    public static final ResourceLocation GIANT = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "giant"));
    public static final ResourceLocation DEPTH_MAGE = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "depth_mage"));
    public static final ResourceLocation INSANITY_COW = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "insanity_cow"));
    public static final ResourceLocation DWARF = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "dwarf"));

    public static final ResourceLocation STRUCTURE_DEFAULT = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "structure_default"));
}