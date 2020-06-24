package com.fi0x.deepmagic.items.skillpoints;

import com.fi0x.deepmagic.mana.player.PlayerProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Objects;

public class SkillpointMaxMana extends SkillpointBasic
{
    public SkillpointMaxMana(String name)
    {
        super(name);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn)
    {
        if(!worldIn.isRemote)
        {
            Objects.requireNonNull(playerIn.getCapability(PlayerProperties.PLAYER_MANA, null)).maxManaMultiplier++;
            return new ActionResult<>(EnumActionResult.SUCCESS, ItemStack.EMPTY);
        }
        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }
}