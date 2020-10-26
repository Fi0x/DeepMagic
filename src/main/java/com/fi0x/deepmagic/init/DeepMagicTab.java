package com.fi0x.deepmagic.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class DeepMagicTab
{
	public static final CreativeTabs BLOCKS = new CreativeTabs("deepmagic_blocks_tab")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ModBlocks.DEEP_CRYSTAL_BLOCK);
		}
	};
	
	public static final CreativeTabs ITEMS = new CreativeTabs("deepmagic_items_tab")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ModItems.DEEP_CRYSTAL);
		}
	};

	public static final CreativeTabs SPELLS = new CreativeTabs("deepmagic_spells_tab")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ModItems.SPELL);
		}
	};
}