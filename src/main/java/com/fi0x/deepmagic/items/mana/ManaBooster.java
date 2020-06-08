package com.fi0x.deepmagic.items.mana;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.PlayerMana;
import com.fi0x.deepmagic.mana.PlayerProperties;
import com.fi0x.deepmagic.util.IHasModel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ManaBooster extends Item implements IHasModel
{
	private static final int BOOST_AMOUNT = 500;
	
	public ManaBooster(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(DeepMagicTab.ITEMS);
		setMaxDamage(5);
		
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
			stack = new ItemStack(Blocks.AIR, 0);
			PlayerMana playerMana = playerIn.getCapability(PlayerProperties.PLAYER_MANA, null);
			playerMana.setMana(playerMana.getMana() + BOOST_AMOUNT);
			playerMana.showMana(playerIn, worldIn);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
	}
}