package com.fi0x.deepmagic.items.mana;

import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import com.fi0x.deepmagic.util.IMagicItem;
import net.minecraft.client.util.ITooltipFlag;
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
import java.util.Objects;

public class Spell extends ItemBase implements IMagicItem
{
    protected int manaCost;
    protected int tier;
    protected double skillXP;
    protected SpellType spellType;
    private final int time;
    private final int radius;
    private final int range;

    public Spell(String name, SpellType type, int tier, int manaCost)
    {
        this(name, type, tier, manaCost, 0);
    }
    public Spell(String name, SpellType type, int tier, int manaCost, int time)
    {
        super(name);
        setCreativeTab(DeepMagicTab.SPELLS);
        setMaxStackSize(1);

        this.tier = tier;
        this.manaCost = manaCost;
        this.skillXP = 10 * tier;
        this.spellType = type;
        this.time = time;
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
            switch(spellType)
            {
                case HEAL:
                    executeHeal(playerIn, playerMana);
                    break;
                case TIME:
                    executeTime(worldIn, playerIn, playerMana);
                    break;
                case WEATHER:
                    executeWeather(worldIn, playerIn, playerMana);
                    break;
                case MOB_ANNIHILATION:
                    executeMobAnnihilation(worldIn, playerIn, playerMana);
                    break;
                case RANGED_MOB_ANNIHILATION:
                    RayTraceResult result = getRayTrace(playerIn);
                    if(result.typeOfHit == RayTraceResult.Type.MISS)
                    {
                        playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "The spell couldn't find a target"));
                        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
                    }
                    executeRangedMobAnnihilation(worldIn, playerIn, playerMana, result);
                    break;
                case MOB_PUSHER:
                    executeMobPusher(worldIn, playerIn, playerMana);
                    break;
            }
        } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "Your spell tier is not high enough"));
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
    @Override
    public void addInformation(@Nonnull ItemStack stack, World worldIn, List<String> tooltip, @Nonnull ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.RED + "This item will be removed in the future");
    }

    private void executeHeal(EntityPlayer playerIn, PlayerMana playerMana)
    {
        if(playerMana.removeMana(manaCost * Math.pow(2, tier - 1)))
        {
            if((Math.random() * playerMana.spellCastSkill) > tier * 2)
            {
                playerIn.heal((int) Math.pow(2, tier));
                addSkillXP(playerIn);
            } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "The spell didn't work"));
        } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have enough mana"));
    }
    private void executeTime(World worldIn, EntityPlayer playerIn, PlayerMana playerMana)
    {
        if(playerMana.removeMana(manaCost))
        {
            if(Math.random() * playerMana.spellCastSkill > 1)
            {
                worldIn.setWorldTime(time);
                playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "Your spell worked"));
                addSkillXP(playerIn);
            } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "The spell didn't work"));
        } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have enough mana"));
    }
    private void executeWeather(World worldIn, EntityPlayer playerIn, PlayerMana playerMana)
    {
        if(playerMana.removeMana(manaCost * tier))
        {
            if((int) (Math.random() * playerMana.spellCastSkill) > tier)
            {
                worldIn.getWorldInfo().setRaining(!worldIn.getWorldInfo().isRaining());
                playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "Your spell worked"));
                addSkillXP(playerIn);
            } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "The spell didn't work"));
        } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have enough mana"));
    }
    private void executeMobAnnihilation(World worldIn, EntityPlayer playerIn, PlayerMana playerMana)
    {
        if(playerMana.removeMana(manaCost * Math.pow(2, tier - 4)))
        {
            if((Math.random() * playerMana.spellCastSkill) > tier)
            {
                BlockPos pos = playerIn.getPosition();
                createExplosions(worldIn, playerIn, pos, radius * (tier - 7));
                addSkillXP(playerIn);
            } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "The spell didn't work"));
        } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have enough mana"));
    }
    private void executeRangedMobAnnihilation(World worldIn, EntityPlayer playerIn, PlayerMana playerMana, RayTraceResult result)
    {
        if(playerMana.removeMana(manaCost * Math.pow(2, tier - 4)))
        {
            if((Math.random() * playerMana.spellCastSkill) > tier)
            {
                BlockPos pos = result.getBlockPos();
                createExplosions(worldIn, playerIn, pos, radius * (tier - 7));
                addSkillXP(playerIn);
            } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "The spell didn't work"));
        } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have enough mana"));
    }
    private void executeMobPusher(World worldIn, EntityPlayer playerIn, PlayerMana playerMana)
    {
        if(playerMana.removeMana(manaCost * Math.pow(2, tier - 3)))
        {
            if((Math.random() * playerMana.spellCastSkill) > tier)
            {
                pushMobs(worldIn, playerIn, radius * (tier - 2), tier);
                addSkillXP(playerIn);
            } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "The spell didn't work"));
        } else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have enough mana"));
    }

    protected void addSkillXP(EntityPlayer player)
    {
        Objects.requireNonNull(player.getCapability(PlayerProperties.PLAYER_MANA, null)).addSkillXP(skillXP);
    }

    private RayTraceResult getRayTrace(EntityPlayer player)
    {
        Vec3d vec3d = player.getPositionEyes(1F);
        Vec3d vec3d1 = player.getLook(1F);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * range * (tier - 7), vec3d1.y * range * (tier - 7), vec3d1.z * range * (tier - 7));
        return player.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
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
    private static void pushMobs(World world, EntityPlayer player, int radius, int tier)
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

    public enum SpellType {
        HEAL,
        TIME,
        WEATHER,
        MOB_ANNIHILATION,
        RANGED_MOB_ANNIHILATION,
        MOB_PUSHER
    }
}