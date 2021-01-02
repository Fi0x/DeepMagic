package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.util.IMagicItem;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

@Deprecated
public class OldSpell extends ItemBase implements IMagicItem
{
    public OldSpell(String name)
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
        return executeSpell(worldIn, playerIn, handIn, compound);
    }

    protected ActionResult<ItemStack> executeSpell(World world, EntityPlayer player, EnumHand hand, NBTTagCompound compound)
    {
        int damage = 0;
        boolean environmentalDmg = false;
        boolean explosion = false;
        int heal = 0;
        int time = 0;
        boolean toggledownfall = false;

        if(compound.hasKey("damage")) damage = compound.getInteger("damage");
        if(compound.hasKey("environmentalDamage")) environmentalDmg = compound.getBoolean("environmentalDamage");
        if(compound.hasKey("explosion")) explosion = compound.getBoolean("explosion");
        if(compound.hasKey("heal")) heal = compound.getInteger("heal");
        if(compound.hasKey("time")) time = compound.getInteger("time");
        if(compound.hasKey("weather")) toggledownfall = compound.getBoolean("weather");

        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
    private static void editMobsInArea(World world, EntityPlayer player, BlockPos pos, int radius, int dmg, int heal, boolean explode, boolean envDmg)
    {
        AxisAlignedBB area = new AxisAlignedBB(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius, pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius);
        List<EntityCreature> entities = world.getEntitiesWithinAABB(EntityCreature.class, area);

        while(!entities.isEmpty())
        {
            if(!entities.get(0).equals(player)) entities.get(0).attackEntityFrom(DamageSource.MAGIC, dmg);
            entities.get(0).heal(heal);
            BlockPos explosionPos = entities.get(0).getPosition();
            if(explode) world.createExplosion(player, explosionPos.getX(), explosionPos.getY(), explosionPos.getZ(), 5, envDmg);
            entities.remove(0);
        }
    }
}
