package com.fi0x.deepmagic.items.mana;

import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.util.IMagicItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

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
        NBTTagCompound compound = itemStack.getTagCompound();
        if(compound == null) compound = new NBTTagCompound();
        if(compound.hasKey("manaCosts")) compound.setInteger("manaCosts", compound.getInteger("manaCosts") + 1);
        else compound.setInteger("manaCosts", 1);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(@Nonnull EntityPlayer player, World worldIn, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote) return EnumActionResult.FAIL;
        ItemStack stack = player.getHeldItem(hand);
        NBTTagCompound compound;
        if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        compound = stack.getTagCompound();

        assert compound != null;
        if(!compound.hasKey("manaCosts")) compound.setInteger("manaCosts", 0);
        if(worldIn.getBlockState(pos).equals(ModBlocks.ATTACK_STONE.getDefaultState()))
        {
            if(compound.hasKey("attackDamage")) compound.setInteger("attackDamage", compound.getInteger("attackDamage") + 1);
            else compound.setInteger("attackDamage", 1);
            compound.setInteger("manaCosts", compound.getInteger("manaCosts") / 10 * 2 + 1);
        }
        System.out.println("Mana Costs: " + compound.getInteger("manaCosts"));
        System.out.println("Attack Damage: " + compound.getInteger("attackDamage"));

        return EnumActionResult.SUCCESS;
    }
}