package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class SpellMobAnnihilation extends SpellBase
{
    int radius;
    double range;

    public SpellMobAnnihilation(String name, int tier)
    {
        super(name, tier);
        this.manaCost = 50;
        this.radius = 10;
        this.range = 100;
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
            if(playerMana.removeMana(manaCost * Math.pow(2, tier - 4)))
            {
                if((Math.random() * playerMana.spellCastSkill) > tier)
                {
                    execute(playerIn, worldIn, tier, radius);
                    addSkillXP(playerIn);
                } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "The spell didn't work"));
            } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have enough mana"));
        } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "Your spell tier is not high enough"));
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    public static void execute(EntityPlayer playerIn, World worldIn, int tier, int radius)
    {
        BlockPos pos = playerIn.getPosition();
        createExplosions(worldIn, playerIn, pos, radius * (tier - 7));
    }

    private static void createExplosions(World world, EntityPlayer player, BlockPos pos, int radius)
    {
        AxisAlignedBB area = new AxisAlignedBB(pos.getX()-radius, pos.getY()-radius, pos.getZ()-radius, pos.getX()+radius, pos.getY()+radius, pos.getZ()+radius);
        List<EntityCreature> entities = world.getEntitiesWithinAABB(EntityCreature.class, area);

        while(!entities.isEmpty())
        {
            BlockPos explosionPos = entities.get(0).getPosition();
            world.createExplosion(player, explosionPos.getX(), explosionPos.getY(), explosionPos.getZ(), 5, false);
            entities.get(0).attackEntityFrom(DamageSource.MAGIC, 50);
            entities.remove(0);
        }
    }
}