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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class SpellRangedMobAnnihilation extends SpellBase
{
    int radius;
    double range;
    public SpellRangedMobAnnihilation(String name, int tier)
    {
        super(name, tier);
        this.manaCost = 100;
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
            RayTraceResult result = getRayTrace(playerIn);
            if(result.typeOfHit == RayTraceResult.Type.MISS)
            {
                playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "The spell couldn't find a target"));
                return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
            }
            if(playerMana.removeMana(manaCost * Math.pow(2, tier - 4)))
            {
                if((Math.random() * playerMana.spellCastSkill) > tier)
                {
                    BlockPos pos = result.getBlockPos();
                    createExplosions(worldIn, playerIn, pos, this.radius * (tier - 7));
                    addSkillXP(playerIn);
                } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "The spell didn't work"));
            } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have enough mana"));
        } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "Your spell tier is not high enough"));
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    private RayTraceResult getRayTrace(EntityPlayer player)
    {
        Vec3d vec3d = player.getPositionEyes(1F);
        Vec3d vec3d1 = player.getLook(1F);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * range * (tier - 7), vec3d1.y * range * (tier - 7), vec3d1.z * range * (tier - 7));
        return player.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
    }
    private void createExplosions(World world, EntityPlayer player, BlockPos pos, int radius)
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