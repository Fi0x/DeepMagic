package com.fi0x.deepmagic.util.handlers;

import com.fi0x.deepmagic.blocks.tileentity.*;
import com.fi0x.deepmagic.commands.CommandDimTeleport;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.particlesystem.ParticleEnum;
import com.fi0x.deepmagic.util.IHasModel;
import com.fi0x.deepmagic.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class RegistryHandler
{
	public static void serverRegistries(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandDimTeleport());
	}
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
		registerTileEntities();
	}
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : ModItems.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}
		for(Block block : ModBlocks.BLOCKS)
		{
			if(block instanceof IHasModel)
			{
				((IHasModel) block).registerModels();
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void spriteAtlasStitching(TextureStitchEvent.Pre event)
	{
		for(ParticleEnum particle : ParticleEnum.values())
		{
			event.getMap().registerSprite(new ResourceLocation(Reference.MOD_ID, "particle/" + particle.getTextureName()));
		}
	}

	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityManaGeneratorNormal.class, new ResourceLocation("mana_generator_normal"));
		GameRegistry.registerTileEntity(TileEntityManaGeneratorInsanity.class, new ResourceLocation("mana_generator_insanity"));
		GameRegistry.registerTileEntity(TileEntityManaGeneratorMob.class, new ResourceLocation("mana_generator_mob"));
		GameRegistry.registerTileEntity(TileEntityManaRelay.class, new ResourceLocation("mana_relay"));

		GameRegistry.registerTileEntity(TileEntityManaAltar.class, new ResourceLocation("mana_altar"));
		GameRegistry.registerTileEntity(TileEntityManaInfuser.class, new ResourceLocation("mana_infuser"));
		GameRegistry.registerTileEntity(TileEntityManaGrinder.class, new ResourceLocation("mana_grinder"));
		GameRegistry.registerTileEntity(TileEntityManaFurnace.class, new ResourceLocation("mana_furnace"));

		GameRegistry.registerTileEntity(TileEntityRune.class, new ResourceLocation("rune"));
		GameRegistry.registerTileEntity(TileEntitySpellStone.class, new ResourceLocation("spell_stone"));
	}
}