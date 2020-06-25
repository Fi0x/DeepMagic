package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class SpellHeal extends SpellBase
{
    public SpellHeal(String name, int tier)
    {
        super(name, tier);
        this.manaCost = 50;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn)
    {
        if(worldIn.isRemote) return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));

        PlayerMana playerMana = playerIn.getCapability(PlayerProperties.PLAYER_MANA, null);
        assert playerMana != null;
        if(playerMana.getSpellTier() >= tier)
        {
            if(playerMana.removeMana(manaCost * Math.pow(2, tier - 1), playerIn))
            {
                if((Math.random() * playerMana.spellCastSkill) > tier * 2)
                {
                    playerIn.heal((int) Math.pow(2, tier));
                } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "The spell didn't work"));
            } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have enough mana"));
        } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "Your spell tier is not high enough"));
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}