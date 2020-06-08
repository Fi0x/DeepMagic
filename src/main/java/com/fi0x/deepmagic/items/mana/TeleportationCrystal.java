package com.fi0x.deepmagic.items.mana;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.commands.Teleport;
import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.PlayerMana;
import com.fi0x.deepmagic.mana.PlayerProperties;
import com.fi0x.deepmagic.util.IHasModel;
import com.fi0x.deepmagic.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class TeleportationCrystal extends Item implements IHasModel
{
	public TeleportationCrystal(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(DeepMagicTab.ITEMS);
		setMaxStackSize(1);
		setMaxDamage(10);
		
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
			if(playerMana.removeMana(50, playerIn))
			{
				playerMana.showMana(playerIn, worldIn);
				int x = playerIn.getPosition().getX();
				int z = playerIn.getPosition().getZ();
				if(playerIn.dimension == 0)
				{
					x*=10;
					z*=10;
					playerIn.sendMessage(new TextComponentString(TextFormatting.BOLD + "You entered a strange dimension..."));
					return teleportEntityTo(worldIn, playerIn, Reference.DIMENSION_ID, x, z, stack);
				} else if(playerIn.dimension == -1)
				{
					x*=8;
					z*=8;
					return teleportEntityTo(worldIn, playerIn, 0, x, z, stack);
				} else if(playerIn.dimension == Reference.DIMENSION_ID)
				{
					x*=0.1;
					z*=0.1;
					playerIn.sendMessage(new TextComponentString(TextFormatting.BOLD + "Your mind clears as you return to the overworld"));
					return teleportEntityTo(worldIn, playerIn, 0, x, z, stack);
				}
				
				return teleportEntityTo(worldIn, playerIn, 0, x, z, stack);
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
	}
	
	private ActionResult<ItemStack> teleportEntityTo(World world, EntityPlayer playerIn, int dimensionID, int x, int z, ItemStack stack)
	{
		playerIn.getEntityWorld().getMinecraftServer().getWorld(dimensionID).getMinecraftServer().getPlayerList().transferPlayerToDimension(
				(EntityPlayerMP) playerIn, dimensionID, new Teleport(playerIn.getEntityWorld().getMinecraftServer().getWorld(dimensionID), x,
				findSurface(playerIn.getEntityWorld().getMinecraftServer().getWorld(dimensionID), x, z), z));
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
	
	private double findSurface(World world, int x, int z)
	{
		int y = world.getHeight();
		boolean foundGround = false;
		
		while(!foundGround && y-- >= 0)
		{
			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			if(block != Blocks.AIR)
			{
				y+=2;
				foundGround = true;
			}
		}
		return y;
	}
}