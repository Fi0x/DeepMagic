package com.fi0x.deepmagic.items.mana;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.PlayerMana;
import com.fi0x.deepmagic.mana.PlayerProperties;
import com.fi0x.deepmagic.util.IHasModel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ManaWaster extends Item implements IHasModel
{
	public ManaWaster(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(DeepMagicTab.ITEMS);
		setMaxStackSize(1);
		
		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		if(!worldIn.isRemote)
		{
			PlayerMana playerMana = playerIn.getCapability(PlayerProperties.PLAYER_MANA, null);
			if(playerMana.removeMana(10, playerIn))
			{
				playerMana.showMana(playerIn, worldIn);
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
			}
			playerMana.showMana(playerIn, worldIn);
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
	}
}