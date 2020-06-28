package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class SpellMobPusher extends SpellBase
{
    int radius;

    public SpellMobPusher(String name, int tier)
    {
        super(name, tier);
        this.manaCost = 50;
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
            if(playerMana.removeMana(manaCost * Math.pow(2, tier - 3), playerIn))
            {
                if((Math.random() * playerMana.spellCastSkill) > tier)
                {
                    pushMobs(worldIn, playerIn, this.radius * (tier - 2));
                    addSkillXP(playerIn);
                } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "The spell didn't work"));
            } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have enough mana"));
        } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "Your spell tier is not high enough"));
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    private void pushMobs(World world, EntityPlayer player, int radius)
    {
        AxisAlignedBB area = new AxisAlignedBB(player.getPosition().getX()-radius, player.getPosition().getY()-radius, player.getPosition().getZ()-radius, player.getPosition().getX()+radius, player.getPosition().getY()+radius, player.getPosition().getZ()+radius);
        List<EntityCreature> entities = world.getEntitiesWithinAABB(EntityCreature.class, area);
        BlockPos playerPos = player.getPosition();

        while(!entities.isEmpty())
        {
            EntityCreature currentEntity = entities.get(0);
            BlockPos creaturePos = currentEntity.getPosition();

            if(playerPos.getX() < creaturePos.getX()) currentEntity.motionX = tier - 2;
            else if(playerPos.getX() > creaturePos.getX()) currentEntity.motionX = -tier - 2;
            else currentEntity.motionX = 0;

            if(playerPos.getZ() < creaturePos.getZ()) currentEntity.motionZ = tier - 2;
            else if(playerPos.getZ() > creaturePos.getZ()) currentEntity.motionZ = -tier - 2;
            else currentEntity.motionZ = 0;

            currentEntity.motionY = 0.5 * (tier - 2);

            entities.remove(0);
        }
    }
}