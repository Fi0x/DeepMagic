package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.DeepMagic;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
																				 DeepMagic.MOD_ID);

	public static final RegistryObject<Block> DEEP_CRYSTAL_BLOCK = registerBlock("deep_crystal_block", () -> new Block(
			BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.HAT).strength(5.0F).explosionResistance(15.0F)
									 .requiresCorrectToolForDrops().sound(SoundType.GLASS)));

	public static final RegistryObject<Block> DEEP_CRYSTAL_ORE = registerBlock("deep_crystal_ore", () -> new Block(
			BlockBehaviour.Properties.of().strength(3.0F).explosionResistance(5.0F)
									 .requiresCorrectToolForDrops().sound(SoundType.STONE)));

	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block)
	{
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}

	private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block)
	{
		return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
