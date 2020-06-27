package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class SpellRangedMobAnnihilation extends SpellBase
{
    int radius;
    public SpellRangedMobAnnihilation(String name, int tier)
    {
        super(name, tier);
        this.manaCost = 100;
        this.radius = 10;
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
            if(playerMana.removeMana(manaCost * Math.pow(2, tier - 4), playerIn))
            {
                if((Math.random() * playerMana.spellCastSkill) > tier)
                {
                    //TODO: Change BlockPos to raycast
                    BlockPos pos = new BlockPos(playerIn.getPosition());
                    createExplosions(worldIn, playerIn, pos, this.radius);
                } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "The spell didn't work"));
            } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have enough mana"));
        } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "Your spell tier is not high enough"));
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    private void createExplosions(World world, EntityPlayer player, BlockPos pos, int radius)
    {
        for(int x = -radius; x <= radius; x++)
        {
            for(int z = -radius; z <= radius; z++)
            {
                world.createExplosion(player, pos.getX() + x, pos.getY() + 1, pos.getZ() + z, 1, false);
                world.createExplosion(player, pos.getX() + x, pos.getY(), pos.getZ() + z, 1, false);
                world.createExplosion(player, pos.getX() + x, pos.getY() -1, pos.getZ() + z, 1, false);
            }
        }
    }
}