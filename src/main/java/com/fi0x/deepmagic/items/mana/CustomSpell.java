package com.fi0x.deepmagic.items.mana;

import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import com.fi0x.deepmagic.util.IMagicItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

public class CustomSpell extends ItemBase implements IMagicItem
{
    public CustomSpell(String name)
    {
        super(name);
        setCreativeTab(DeepMagicTab.SPELLS);
        setMaxStackSize(1);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn)
    {
        if(worldIn.isRemote) return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        NBTTagCompound compound;
        if(!itemStack.hasTagCompound()) itemStack.setTagCompound(new NBTTagCompound());
        compound = itemStack.getTagCompound();
        assert compound != null;

        if(playerIn.isSneaking())
        {
            BlockPos blockPos = getFocusedBlock(playerIn, 5);
            if(blockPos != null)
            {
                IBlockState block = worldIn.getBlockState(blockPos);
                if(true) //TODO: Check if block is a Spell Stone
                {
                    playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Spell Stone clicked"));
                }
            } else
            {
                //TODO: Needs to be removed, only for testing
                compound.setInteger("manaCosts", 10);
                compound.setInteger("tier", 0);
                compound.setInteger("range", 10);
                compound.setInteger("target", 0);
                compound.setInteger("radius", 0);
                compound.setInteger("damage", 0);
                compound.setBoolean("environmentalDamage", false);
                compound.setBoolean("explosion", false);
                compound.setInteger("heal", 5);
                compound.setInteger("time", 10);
                compound.setBoolean("weather", true);
                playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Stats set"));
                return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
            }

            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }

        PlayerMana playerMana = playerIn.getCapability(PlayerProperties.PLAYER_MANA, null);
        assert playerMana != null;
        if(compound.hasKey("manaCosts") && !(playerMana.removeMana(compound.getDouble("manaCosts"))))
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have enough mana"));
            return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }
        if(compound.hasKey("tier") && playerMana.getSpellTier() < compound.getInteger("tier"))
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You are not skilled enough for this spell"));
            return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }

        int range = 0;
        EntityLivingBase targetEntity = null;
        BlockPos targetPos = null;
        int radius = 0;

        int damage = 0;
        boolean environmentalDmg = false;
        boolean explosion = false;
        int heal = 0;
        int time = 0;
        boolean toggledownfall = false;

        if(compound.hasKey("range")) range = compound.getInteger("range");
        if(compound.hasKey("target"))
        {
            int targetCode = compound.getInteger("target");
            if(targetCode == 0) targetEntity = playerIn;
            if(targetCode == 1) targetPos = playerIn.getPosition();
            if(targetCode == 2) targetPos = getFocusedBlock(playerIn, range);
            if(targetCode == 3) targetEntity = (EntityLivingBase) getFocusedEntity(playerIn);
        }
        if(compound.hasKey("radius")) radius = compound.getInteger("radius");

        if(compound.hasKey("damage")) damage = compound.getInteger("damage");
        if(compound.hasKey("environmentalDamage")) environmentalDmg = compound.getBoolean("environmentalDamage");
        if(compound.hasKey("explosion")) explosion = compound.getBoolean("explosion");
        if(compound.hasKey("heal")) heal = compound.getInteger("heal");
        if(compound.hasKey("time")) time = compound.getInteger("time");
        if(compound.hasKey("weather")) toggledownfall = compound.getBoolean("weather");

        if(targetEntity != null)
        {
            targetEntity.heal(heal);
            targetEntity.attackEntityFrom(DamageSource.MAGIC, damage);
            editMobsInArea(worldIn, playerIn, targetEntity.getPosition(), radius, damage, heal, explosion, environmentalDmg);
        }
        if(targetPos != null)
        {
            editMobsInArea(worldIn, playerIn, targetPos, radius, damage, heal, explosion, environmentalDmg);
        }
        if(time != 0) worldIn.setWorldTime(time);
        if(toggledownfall) worldIn.getWorldInfo().setRaining(!worldIn.getWorldInfo().isRaining());

        if(compound.hasKey("skillXP")) playerMana.addSkillXP(compound.getDouble("skillXP"));
        playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "Spell executed"));
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
    @Override
    public void addInformation(ItemStack stack, World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn)
    {
        NBTTagCompound compound;
        if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        compound = stack.getTagCompound();
        assert compound != null;
        if(GuiScreen.isShiftKeyDown())
        {
            tooltip.add(TextFormatting.WHITE + "Spell Effects:");
            if(compound.hasKey("target")) tooltip.add(TextFormatting.WHITE + "Target: " + compound.getInteger("target"));
            if(compound.hasKey("range")) tooltip.add(TextFormatting.WHITE + "Range: " + compound.getInteger("range"));
            if(compound.hasKey("radius")) tooltip.add(TextFormatting.WHITE + "Radius: " + compound.getInteger("radius"));
            if(compound.hasKey("damage")) tooltip.add(TextFormatting.RED + "Damage: " + compound.getInteger("damage"));
            if(compound.hasKey("environmentalDamage") && compound.getBoolean("environmentalDamage")) tooltip.add(TextFormatting.RED + "Does environmental damage" );
            if(compound.hasKey("explosion") && compound.getBoolean("explosion")) tooltip.add(TextFormatting.RED + "Creates an explosion");
            if(compound.hasKey("heal")) tooltip.add(TextFormatting.GREEN + "Healing amount: " + compound.getInteger("heal"));
            if(compound.hasKey("time")) tooltip.add(TextFormatting.WHITE + "Set Time to: " + compound.getInteger("time"));
            if(compound.hasKey("weather") && compound.getBoolean("weather")) tooltip.add(TextFormatting.WHITE + "Can toggle downfall");
        } else tooltip.add(TextFormatting.YELLOW + "Press Shift for more Information");
    }

    private BlockPos getFocusedBlock(EntityPlayer player, int range)
    {
        Vec3d vec3d = player.getPositionEyes(1F);
        Vec3d vec3d1 = player.getLook(1F);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * range, vec3d1.y * range, vec3d1.z * range);
        RayTraceResult result = player.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
        assert result != null;
        if(result.typeOfHit == RayTraceResult.Type.MISS) return null;
        return result.getBlockPos();
    }
    private Entity getFocusedEntity(EntityPlayer player)
    {
        //TODO make function return targeted entity instead of player
        Vec3d vec3d = player.getPositionEyes(1F);
        Vec3d vec3d1 = player.getLook(1F);
        RayTraceResult result = player.world.rayTraceBlocks(vec3d, vec3d1, false, false, true);
        assert result != null;
        if(result.typeOfHit == RayTraceResult.Type.MISS) return null;
        return player;
    }
    private static void editMobsInArea(World world, EntityPlayer player, BlockPos pos, int radius, int dmg, int heal, boolean explode, boolean envDmg)
    {
        AxisAlignedBB area = new AxisAlignedBB(pos.getX()-radius, pos.getY()-radius, pos.getZ()-radius, pos.getX()+radius, pos.getY()+radius, pos.getZ()+radius);
        List<EntityCreature> entities = world.getEntitiesWithinAABB(EntityCreature.class, area);

        while(!entities.isEmpty())
        {
            entities.get(0).attackEntityFrom(DamageSource.MAGIC, dmg);
            entities.get(0).heal(heal);
            BlockPos explosionPos = entities.get(0).getPosition();
            if(explode) world.createExplosion(player, explosionPos.getX(), explosionPos.getY(),  explosionPos.getZ(), 5, envDmg);
            entities.remove(0);
        }
    }
}