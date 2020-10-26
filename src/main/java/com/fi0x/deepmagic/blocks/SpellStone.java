package com.fi0x.deepmagic.blocks;

import com.fi0x.deepmagic.entities.tileentity.BlockTileEntity;
import com.fi0x.deepmagic.entities.tileentity.TileEntitySpellStone;
import com.fi0x.deepmagic.items.mana.Spell;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SpellStone extends BlockTileEntity<TileEntitySpellStone>
{
    public SpellStone(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.METAL);
        setHardness(5.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote) return false;
        ItemStack stack = playerIn.getHeldItem(hand);
        Item item = stack.getItem();
        TileEntitySpellStone tile = getTileEntity(worldIn, pos);

        if(item instanceof Spell) return chargeSpell(playerIn, stack, tile);

        if(item instanceof ItemSword && tile.getAttackDmg() < 1)
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You set the damage value to 1"));
            tile.setAttackDmg(1);
        } else if(item instanceof ItemBow)
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You set the target to a targeted entity"));
            tile.setTarget(4);
        } else if(item instanceof ItemSnowball)
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You increased the range by 2"));
            tile.increaseRange(2);
        } else return false;
        stack.setCount(stack.getCount() - 1);
        return true;
    }
    @Override
    public Class<TileEntitySpellStone> getTileEntityClass()
    {
        return TileEntitySpellStone.class;
    }
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntitySpellStone();
    }

    private boolean chargeSpell(EntityPlayer playerIn, ItemStack stack, TileEntitySpellStone tile)
    {
        playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Spell Stone used"));
        NBTTagCompound compound;
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        compound = stack.getTagCompound();
        assert compound != null;

        int manaCosts = 0;
        if(compound.hasKey("manaCosts")) manaCosts = compound.getInteger("manaCosts");

        if(compound.getInteger("range") < tile.getRange())
        {
            compound.setInteger("range", tile.getRange());
            manaCosts = tile.resetRange(manaCosts);
        }
        if(tile.getTarget() != 0)
        {
            compound.setInteger("target", tile.getTarget() - 1);
            manaCosts = tile.resetTarget(manaCosts);
        }
        if(compound.getInteger("radius") < tile.getRadius())
        {
            compound.setInteger("radius", tile.getRadius());
            manaCosts = tile.resetRadius(manaCosts);
        }
        if(compound.getInteger("damage") < tile.getAttackDmg())
        {
            compound.setInteger("damage", tile.getAttackDmg());
            manaCosts = tile.resetAttackDmg(manaCosts);
        }
        if(tile.doesEnvDmg())
        {
            compound.setBoolean("environmentalDamage", true);
            manaCosts = tile.resetEnvDmg(manaCosts);
        }
        if(tile.isExplosive())
        {
            compound.setBoolean("explosion", true);
            manaCosts = tile.resetExplosive(manaCosts);
        }
        if(compound.getInteger("heal") < tile.getHeal())
        {
            compound.setInteger("heal", tile.getHeal());
            manaCosts = tile.resetHeal(manaCosts);
        }
        if(tile.getTime() != 0)
        {
            compound.setInteger("time", tile.getTime());
            manaCosts = tile.resetTime(manaCosts);
        }
        if(tile.doesWeather())
        {
            compound.setBoolean("weather", true);
            manaCosts = tile.resetWeather(manaCosts);
        }

        compound.setInteger("manaCosts", manaCosts);
        compound.setInteger("tier", manaCosts / 100);
        return true;
    }
}