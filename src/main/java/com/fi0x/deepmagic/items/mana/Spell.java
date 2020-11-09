package com.fi0x.deepmagic.items.mana;

import com.fi0x.deepmagic.blocks.SpellStone;
import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import com.fi0x.deepmagic.util.IMagicItem;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.client.Minecraft;
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

public class Spell extends ItemBase implements IMagicItem
{
    public Spell(String name)
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

        BlockPos blockPos = getFocusedBlock(playerIn, 5);
        if(blockPos != null && worldIn.getBlockState(blockPos).getBlock() instanceof SpellStone) return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));

        PlayerMana playerMana = playerIn.getCapability(PlayerProperties.PLAYER_MANA, null);
        assert playerMana != null;
        if(compound.hasKey("tier") && playerMana.getSpellTier() < compound.getInteger("tier"))
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You are not skilled enough for this spell"));
            return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }
        if(compound.hasKey("manaCosts") && !(playerMana.removeMana(compound.getDouble("manaCosts"))))
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have enough mana"));
            return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }

        if(compound.hasKey("skillXP")) playerMana.addSkillXP(playerIn, compound.getDouble("skillXP"));
        return executeSpell(worldIn, playerIn, handIn, compound);
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
            tooltip.add(TextFormatting.GREEN + "Spell Effects:");
            if(compound.hasKey("damage")) tooltip.add(TextFormatting.GREEN + "Damage: " + compound.getInteger("damage"));
            if(compound.hasKey("environmentalDamage") && compound.getBoolean("environmentalDamage")) tooltip.add(TextFormatting.GREEN + "Does environmental damage" );
            if(compound.hasKey("explosion") && compound.getBoolean("explosion")) tooltip.add(TextFormatting.GREEN + "Creates an explosion");
            if(compound.hasKey("heal")) tooltip.add(TextFormatting.GREEN + "Healing amount: " + compound.getInteger("heal"));
            if(compound.hasKey("time")) tooltip.add(TextFormatting.GREEN + "Add " + compound.getInteger("time") + " timeunits");
            if(compound.hasKey("weather") && compound.getBoolean("weather")) tooltip.add(TextFormatting.GREEN + "Can toggle downfall");
            tooltip.add(TextFormatting.WHITE + "Modifications:");
            if(compound.hasKey("range")) tooltip.add(TextFormatting.WHITE + "Range: " + compound.getInteger("range"));
            if(compound.hasKey("radius")) tooltip.add(TextFormatting.WHITE + "Radius: " + compound.getInteger("radius"));
            tooltip.add(TextFormatting.YELLOW + "Targets:");
            if(compound.hasKey("targetSelf") && compound.getBoolean("targetSelf")) tooltip.add(TextFormatting.YELLOW + "Yourself");
            if(compound.hasKey("targetSelfPos") && compound.getBoolean("targetSelfPos")) tooltip.add(TextFormatting.YELLOW + "Your Position");
            if(compound.hasKey("targetFocus") && compound.getBoolean("targetFocus")) tooltip.add(TextFormatting.YELLOW + "Targeted Entity");
            if(compound.hasKey("targetFocusPos") && compound.getBoolean("targetFocusPos")) tooltip.add(TextFormatting.YELLOW + "Targeted Position");
        } else tooltip.add(TextFormatting.YELLOW + "Press Shift for more Information");
        if(GuiScreen.isCtrlKeyDown())
        {
            if(compound.hasKey("manaCosts")) tooltip.add(TextFormatting.BLUE + "Consumes " + compound.getInteger("manaCosts") + " Mana");
            else tooltip.add(TextFormatting.BLUE + "Consumes " + ConfigHandler.spellBaseManaCost + " Mana");
            if(compound.hasKey("tier")) tooltip.add(TextFormatting.BLUE + "Requires Skill Tier " + compound.getInteger("tier"));
        } else tooltip.add(TextFormatting.BLUE + "Press Ctrl for Mana Information");
    }

    protected ActionResult<ItemStack> executeSpell(World world, EntityPlayer player, EnumHand hand, NBTTagCompound compound)
    {
        int range = 0;
        EntityLivingBase targetEntity1 = null;
        EntityLivingBase targetEntity2 = null;
        BlockPos targetPos1 = null;
        BlockPos targetPos2 = null;
        int radius = 0;

        int damage = 0;
        boolean environmentalDmg = false;
        boolean explosion = false;
        int heal = 0;
        int time = 0;
        boolean toggledownfall = false;

        if(compound.hasKey("range")) range = compound.getInteger("range");
        if(compound.hasKey("targetSelf")) targetEntity1 = player;
        if(compound.hasKey("targetSelfPos")) targetPos1 = player.getPosition();
        if(compound.hasKey("targetFocus")) targetEntity2 = (EntityLivingBase) getFocusedEntity();
        if(compound.hasKey("targetFocusPos")) targetPos2 = getFocusedBlock(player, range);
        if(compound.hasKey("radius")) radius = compound.getInteger("radius");

        if(compound.hasKey("damage")) damage = compound.getInteger("damage");
        if(compound.hasKey("environmentalDamage")) environmentalDmg = compound.getBoolean("environmentalDamage");
        if(compound.hasKey("explosion")) explosion = compound.getBoolean("explosion");
        if(compound.hasKey("heal")) heal = compound.getInteger("heal");
        if(compound.hasKey("time")) time = compound.getInteger("time");
        if(compound.hasKey("weather")) toggledownfall = compound.getBoolean("weather");

        if(targetEntity1 != null)
        {
            targetEntity1.heal(heal);
            editMobsInArea(world, player, targetEntity1.getPosition(), radius, damage, heal, explosion, environmentalDmg);
        }
        if(targetPos1 != null) editMobsInArea(world, player, targetPos1, radius, damage, heal, explosion, environmentalDmg);
        if(targetEntity2 != null)
        {
            targetEntity2.heal(heal);
            targetEntity2.attackEntityFrom(DamageSource.MAGIC, damage);
            editMobsInArea(world, player, targetEntity2.getPosition(), radius, damage, heal, explosion, environmentalDmg);
        }
        if(targetPos2 != null) editMobsInArea(world, player, targetPos2, radius, damage, heal, explosion, environmentalDmg);
        if(time != 0) world.setWorldTime(world.getWorldTime() + time);
        if(toggledownfall) world.getWorldInfo().setRaining(!world.getWorldInfo().isRaining());

        assert player != null;
        player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Spell executed"));
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
    protected BlockPos getFocusedBlock(EntityPlayer player, int range)
    {
        Vec3d vec3d = player.getPositionEyes(1F);
        Vec3d vec3d1 = player.getLook(1F);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * range, vec3d1.y * range, vec3d1.z * range);
        RayTraceResult result = player.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
        assert result != null;
        if(result.typeOfHit == RayTraceResult.Type.MISS) return null;
        return result.getBlockPos();
    }
    private Entity getFocusedEntity()
    {
        Minecraft mc = Minecraft.getMinecraft();
        RayTraceResult rtResult = mc.objectMouseOver;
        mc.entityRenderer.getMouseOver(0);
        Entity target = rtResult.entityHit;
        System.out.println("Target: " + target);
        return target;
    }
    private static void editMobsInArea(World world, EntityPlayer player, BlockPos pos, int radius, int dmg, int heal, boolean explode, boolean envDmg)
    {
        AxisAlignedBB area = new AxisAlignedBB(pos.getX()-radius, pos.getY()-radius, pos.getZ()-radius, pos.getX()+radius, pos.getY()+radius, pos.getZ()+radius);
        List<EntityCreature> entities = world.getEntitiesWithinAABB(EntityCreature.class, area);

        while(!entities.isEmpty())
        {
            if(!entities.get(0).equals(player)) entities.get(0).attackEntityFrom(DamageSource.MAGIC, dmg);
            entities.get(0).heal(heal);
            BlockPos explosionPos = entities.get(0).getPosition();
            if(explode) world.createExplosion(player, explosionPos.getX(), explosionPos.getY(),  explosionPos.getZ(), 5, envDmg);
            entities.remove(0);
        }
    }
}