package com.fi0x.deepmagic.items.mana;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.util.IHasModel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ManaLinker extends Item implements IHasModel
{
	public ManaLinker(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(DeepMagicTab.ITEMS);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	@Override
	public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn)
	{
		NBTTagCompound compound;
		if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		compound = stack.getTagCompound();
		assert compound != null;

		tooltip.add(TextFormatting.WHITE + "Can link a Block to the selected Mana Altar");
		if(GuiScreen.isShiftKeyDown())
		{
			if(compound.hasKey("linked") && compound.getBoolean("linked"))
			{
				int linkX =compound.getInteger("x");
				int linkY =compound.getInteger("y");
				int linkZ =compound.getInteger("z");
				tooltip.add(TextFormatting.YELLOW + "Linked to: " + linkX + ", " + linkY + ", " + linkZ);
			}
			else tooltip.add(TextFormatting.YELLOW + "Not linked");
		} else tooltip.add(TextFormatting.YELLOW + "Press Shift for more Information");
	}
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn)
	{
		if(worldIn.isRemote) return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
		ItemStack itemStack = playerIn.getHeldItem(handIn);
		NBTTagCompound compound;
		if(!itemStack.hasTagCompound()) itemStack.setTagCompound(new NBTTagCompound());
		compound = itemStack.getTagCompound();
		if(playerIn.isSneaking())
		{
			assert compound != null;
			compound.setBoolean("linked", false);
			return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}
		return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
	}
}