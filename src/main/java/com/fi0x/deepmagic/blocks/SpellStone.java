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

        if(item instanceof ItemSnowball)
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You increased the range by 2"));
            tile.increaseRange(2);
        } else if(item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD") && !tile.isTargetSelf())
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You set the target to yourself"));
            tile.setTargetSelf();
        } else if(item.getUnlocalizedName().equals("tile.stone") && !tile.isTargetSelfPos())
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You set the target to your position"));
            tile.setTargetSelfPos();
        } else if(item instanceof ItemArrow && !tile.isTargetFocus())
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You set the target to your targeted block"));
            tile.setTargetFocus();
        } else if(item instanceof ItemBow && !tile.isTargetFocusPos())
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You set the target to a targeted entity"));
            tile.setTargetFocusPos();
        } else if(item instanceof ItemSplashPotion)
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You increased the radius by 2"));
            tile.increaseRadius(2);
        } else if(item instanceof ItemSword && tile.getAttackDmg() < 1 && ((ItemSword) item).getToolMaterialName().equals("IRON"))
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You set the damage value to 2"));
            tile.setAttackDmg(2);
        } else if(item instanceof ItemFlintAndSteel)
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You added Environmental Damage"));
            tile.setEnvDmg();
        } else if(item.getUnlocalizedName().equals("tile.tnt"))
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You added an explosion"));
            tile.setExplosive();
        } else if(item instanceof ItemAppleGold)
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You increased the healing by 1"));
            tile.increaseHeal(1);
        } else if(item instanceof ItemClock)
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You added 1hr to the time"));
            tile.addTime(1000);
        } else if(item instanceof ItemBucket)
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "Your spell will change the weather"));
            tile.setWeather();
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

        int manaCosts = 10;
        if(compound.hasKey("manaCosts")) manaCosts = compound.getInteger("manaCosts");

        if(tile.getRange() > 0)
        {
            compound.setInteger("range", compound.getInteger("range") + tile.getRange());
            manaCosts = tile.resetRange(manaCosts);
        }
        if(tile.isTargetSelf())
        {
            compound.setBoolean("targetSelf", true);
            manaCosts = tile.resetTargetSelf(manaCosts);
        }
        if(tile.isTargetSelfPos())
        {
            compound.setBoolean("targetSelfPos", true);
            manaCosts = tile.resetTargetSelfPos(manaCosts);
        }
        if(tile.isTargetFocus())
        {
            compound.setBoolean("targetFocus", true);
            manaCosts = tile.resetTargetFocus(manaCosts);
        }
        if(tile.isTargetFocusPos())
        {
            compound.setBoolean("targetFocusPos", true);
            manaCosts = tile.resetTargetFocusPos(manaCosts);
        }
        if(tile.getRadius() > 0)
        {
            compound.setInteger("radius", compound.getInteger("radius") + tile.getRadius());
            manaCosts = tile.resetRadius(manaCosts);
        }
        if(tile.getAttackDmg() > 0 && ((compound.hasKey("damage") && compound.getInteger("damage") < tile.getAttackDmg()) || !compound.hasKey("damage")))
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
        if(tile.getHeal() > 0 && ((compound.hasKey("heal") || compound.getInteger("heal") < tile.getHeal()) || !compound.hasKey("heal")))
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
        compound.setInteger("tier", manaCosts / 1000);
        compound.setDouble("skillXP", (double) manaCosts / 100);
        return true;
    }
}