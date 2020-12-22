package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.blocks.SpellStone;
import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class ManaChargedSpell extends Spell
{
    public ManaChargedSpell(String name)
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

        BlockPos blockPos = getFocusedBlock(playerIn, 5);
        if(blockPos != null && worldIn.getBlockState(blockPos).getBlock() instanceof SpellStone) return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));

        PlayerMana playerMana = playerIn.getCapability(PlayerProperties.PLAYER_MANA, null);
        assert playerMana != null;
        if(compound.hasKey("tier") && playerMana.getSpellTier() < compound.getInteger("tier"))
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You are not skilled enough for this spell"));
            return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }

        if(!compound.hasKey("manaCharge"))
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "The spell is not charged"));
            return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }
        if(compound.hasKey("manaCosts") && compound.getInteger("manaCosts") > compound.getInteger("manaCharge"))
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "The spell has not enough charge"));
            return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }

        return executeSpell(worldIn, playerIn, handIn, compound);
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
            tooltip.add(TextFormatting.BLUE + "Uses Charged Mana instead of Player Mana");
            if(compound.hasKey("manaCharge")) tooltip.add(TextFormatting.BLUE + "Charged Mana: " + compound.getInteger("manaCharge"));
        }
    }
}