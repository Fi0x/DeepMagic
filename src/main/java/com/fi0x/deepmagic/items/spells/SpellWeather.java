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

public class SpellWeather extends SpellBase
{
    public SpellWeather(String name, int tier)
    {
        super(name, tier);
        this.manaCost = 100;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn)
    {
        if(worldIn.isRemote) return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));

        PlayerMana playerMana = playerIn.getCapability(PlayerProperties.PLAYER_MANA, null);
        assert playerMana != null;
        if(playerMana.removeMana(manaCost * tier, playerIn))
        {
            if((int) (Math.random() * (5 + tier)) > 4)
            {
                worldIn.getWorldInfo().setRaining(!worldIn.getWorldInfo().isRaining());
                playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "Your spell worked"));
            }
            else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "The spell didn't work"));
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}