package com.fi0x.deepmagic;

import com.fi0x.deepmagic.init.*;
import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerPropertyEvents;
import com.fi0x.deepmagic.proxy.CommonProxy;
import com.fi0x.deepmagic.util.Reference;
import com.fi0x.deepmagic.util.handlers.*;
import com.fi0x.deepmagic.world.generators.ModWorldGen;
import com.fi0x.deepmagic.world.generators.WorldGenCustomStructures;
import com.fi0x.deepmagic.world.generators.WorldGenCustomTrees;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.File;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main
{
	private static org.apache.logging.log4j.Logger logger;
	public static File config;

	@Mod.Instance
	public static Main instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	static
	{
		FluidRegistry.enableUniversalBucket();
	}

	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.registerConfig(event);
		proxy.preInit(event);
		PacketHandler.registerMessages(Reference.MOD_ID);
		ModFluids.registerFluids();
		EntityInit.registerEntities();
		RenderHandler.registerEntityRenders();
		RenderHandler.registerCustomMeshesAndStates();
		BiomeInit.registerBiomes();
		DimensionInit.registerDimensions();
		GameRegistry.registerWorldGenerator(new ModWorldGen(), 3);
		GameRegistry.registerWorldGenerator(new WorldGenCustomStructures(), 0);
		WorldGenCustomTrees.register();
		RegistryHandler.registerTileEntities();

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
		SoundsHandler.registerSounds();
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

	public static Logger getLogger()
	{
		if(logger == null) logger = LogManager.getFormatterLogger(Reference.MOD_ID);
		return logger;
	}
}