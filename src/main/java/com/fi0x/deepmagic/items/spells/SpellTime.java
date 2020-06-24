package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class SpellTime extends SpellBase
{
    private final int time;
    public SpellTime(String name, int time)
    {
        super(name, 0);
        this.time = time;
        this.manaCost = 500;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn)
    {
        if(worldIn.isRemote) return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));

        PlayerMana playerMana = playerIn.getCapability(PlayerProperties.PLAYER_MANA, null);
        assert playerMana != null;
        if(playerMana.removeMana(manaCost, playerIn))
        {
            worldIn.setWorldTime(time);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}