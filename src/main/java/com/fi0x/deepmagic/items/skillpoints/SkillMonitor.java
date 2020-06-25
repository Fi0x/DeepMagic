package com.fi0x.deepmagic.items.skillpoints;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import com.fi0x.deepmagic.util.IMagicItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class SkillMonitor extends ItemBase implements IMagicItem
{
    public SkillMonitor(String name)
    {
        super(name);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn)
    {
        if(!worldIn.isRemote)
        {
            PlayerMana playerMana = playerIn.getCapability(PlayerProperties.PLAYER_MANA, null);
            Main.proxy.openSkilltreeGui(playerMana);
            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }
}