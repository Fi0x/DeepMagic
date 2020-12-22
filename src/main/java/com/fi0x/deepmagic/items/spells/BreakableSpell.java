package com.fi0x.deepmagic.items.spells;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class BreakableSpell extends Spell
{
    public BreakableSpell(String name)
    {
        super(name);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn)
    {
        if(worldIn.isRemote) return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        NBTTagCompound compound;
        if(!itemStack.hasTagCompound()) itemStack.setTagCompound(new NBTTagCompound());
        compound = itemStack.getTagCompound();
        assert compound != null;

        executeSpell(worldIn, playerIn, handIn, compound);
        itemStack.setCount(itemStack.getCount() - 1);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }
    @Override
    public void addInformation(ItemStack stack, World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        NBTTagCompound compound;
        if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        compound = stack.getTagCompound();
        assert compound != null;

        if(GuiScreen.isCtrlKeyDown())
        {
            tooltip.add(TextFormatting.BLUE + "This Spell will break when used and won't consume Mana");
        }
    }
}