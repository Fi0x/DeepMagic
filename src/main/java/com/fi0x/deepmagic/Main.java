package com.fi0x.deepmagic;

import com.fi0x.deepmagic.init.BiomeInit;
import com.fi0x.deepmagic.init.DimensionInit;
import com.fi0x.deepmagic.init.EntityInit;
import com.fi0x.deepmagic.init.ModRecipes;
import com.fi0x.deepmagic.mana.PlayerMana;
import com.fi0x.deepmagic.mana.PlayerPropertyEvents;
import com.fi0x.deepmagic.proxy.CommonProxy;
import com.fi0x.deepmagic.util.Reference;
import com.fi0x.deepmagic.util.handlers.RegistryHandler;
import com.fi0x.deepmagic.world.generators.ModWorldGen;
import com.fi0x.deepmagic.world.generators.WorldGenCustomStructures;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main
{
	@Mod.Instance
	public static Main instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		BiomeInit.registerBiomes();
		DimensionInit.registerDimensions();
		GameRegistry.registerWorldGenerator(new ModWorldGen(), 3);
		GameRegistry.registerWorldGenerator(new WorldGenCustomStructures(), 0);
		EntityInit.registerEntities();

		MinecraftForge.EVENT_BUS.register(PlayerPropertyEvents.instance);
		CapabilityManager.INSTANCE.register(PlayerMana.class, new Capability.IStorage<PlayerMana>() {
			@Nullable
			@Override
			public NBTBase writeNBT(Capability<PlayerMana> capability, PlayerMana instance, EnumFacing side) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void readNBT(Capability<PlayerMana> capability, PlayerMana instance, EnumFacing side, NBTBase nbt) {
				throw new UnsupportedOperationException();
			}
		}, () -> null);
	}

	@Mod.EventHandler
	public static void init(FMLInitializationEvent event)
	{
		ModRecipes.init();
	}

	@Mod.EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
	}

	@Mod.EventHandler
	public static void serverInit(FMLServerStartingEvent event)
	{
		RegistryHandler.serverRegistries(event);
	}
}