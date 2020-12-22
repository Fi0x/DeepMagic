package com.fi0x.deepmagic.blocks;

import com.fi0x.deepmagic.blocks.tileentity.BlockTileEntity;
import com.fi0x.deepmagic.blocks.tileentity.TileEntitySpellStone;
import com.fi0x.deepmagic.items.spells.Spell;
import com.fi0x.deepmagic.items.spells.SpellComponent;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
        setSoundType(SoundType.STONE);
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

        if(item instanceof SpellComponent)
        {
            NBTTagCompound compound;
            if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
            compound = stack.getTagCompound();
            assert compound != null;

            if(compound.hasKey("effect"))
            {
                switch(compound.getByte("effect"))
                {
                    case 0:
                        playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You increased the damage value by 1"));
                        tile.increaseAttackDmg(2);
                        break;
                    case 1:
                        playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You added environmental damage"));
                        tile.setEnvDmg();
                        break;
                    case 2:
                        playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You added an explosion effect"));
                        tile.setExplosive();
                        break;
                    case 3:
                        playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You increased the healing by 1"));
                        tile.increaseHeal(1);
                        break;
                    case 4:
                        playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You added 1hr to the time"));
                        tile.addTime(1000);
                        break;
                    case 5:
                        playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "Your spell will change the weather"));
                        tile.setWeather();
                        break;
                    default:
                        playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "This component didn't do anything"));
                        break;
                }
            }
            if(compound.hasKey("modifier"))
            {
                switch(compound.getByte("modifier"))
                {
                    case 0:
                        playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You increased the range by 2"));
                        tile.increaseRange(2);
                        break;
                    case 1:
                        playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You increased the radius by 2"));
                        tile.increaseRadius(2);
                        break;
                    default:
                        playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "This component didn't do anything"));
                        break;
                }
            }
            if(compound.hasKey("target"))
            {
                switch(compound.getByte("target"))
                {
                    case 0:
                        playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You set the target to yourself"));
                        tile.setTargetSelf();
                        break;
                    case 1:
                        playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You set the target to your position"));
                        tile.setTargetSelfPos();
                        break;
                    case 2:
                        playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You set the target to a focused entity"));
                        tile.setTargetFocus();
                        break;
                    case 3:
                        playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You set the target to a focused block"));
                        tile.setTargetFocusPos();
                        break;
                    default:
                        playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "This component didn't do anything"));
                        break;
                }
            }
            stack.shrink(1);
        }
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
        if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        compound = stack.getTagCompound();
        assert compound != null;

        if(tile.getRange() > 0)
        {
            if(compound.hasKey("range"))compound.setInteger("range", compound.getInteger("range") + tile.getRange());
            else compound.setInteger("range", tile.getRange());
            tile.resetRange();
        }
        if(tile.isTargetSelf())
        {
            compound.setBoolean("targetSelf", true);
            tile.resetTargetSelf();
        }
        if(tile.isTargetSelfPos())
        {
            compound.setBoolean("targetSelfPos", true);
            tile.resetTargetSelfPos();
        }
        if(tile.isTargetFocus())
        {
            compound.setBoolean("targetFocus", true);
            tile.resetTargetFocus();
        }
        if(tile.isTargetFocusPos())
        {
            compound.setBoolean("targetFocusPos", true);
            tile.resetTargetFocusPos();
        }
        if(tile.getRadius() > 0)
        {
            if(compound.hasKey("radius")) compound.setInteger("radius", compound.getInteger("radius") + tile.getRadius());
            else compound.setInteger("radius", tile.getRadius());
            tile.resetRadius();
        }
        if(tile.getAttackDmg() > 0)
        {
            if(compound.hasKey("damage")) compound.setInteger("damage", compound.getInteger("damage") + tile.getAttackDmg());
            else compound.setInteger("damage", tile.getAttackDmg());
            tile.resetAttackDmg();
        }
        if(tile.doesEnvDmg())
        {
            compound.setBoolean("environmentalDamage", true);
            tile.resetEnvDmg();
        }
        if(tile.isExplosive())
        {
            compound.setBoolean("explosion", true);
            tile.resetExplosive();
        }
        if(tile.getHeal() > 0)
        {
            if(compound.hasKey("heal")) compound.setInteger("heal", compound.getInteger("heal") + tile.getHeal());
            else compound.setInteger("heal", tile.getHeal());
            tile.resetHeal();
        }
        if(tile.getTime() != 0)
        {
            if(compound.hasKey("time")) compound.setInteger("time", compound.getInteger("time") + tile.getTime());
            else compound.setInteger("time", tile.getTime());
            tile.resetTime();
        }
        if(tile.doesWeather())
        {
            compound.setBoolean("weather", true);
            tile.resetWeather();
        }

        int manaBase = ConfigHandler.spellBaseManaCost;
        if(compound.hasKey("manaBase")) manaBase = compound.getInteger("manaBase");
        manaBase += tile.getManaAdder();
        compound.setInteger("manaBase", manaBase);
        tile.resetManaAdder();

        double manaMult = 1;
        if(compound.hasKey("manaMultiplier")) manaMult = compound.getDouble("manaMultiplier");
        manaMult += tile.getManaMultiplier();
        compound.setDouble("manaMultiplier", manaMult);
        tile.resetManaMultiplier();

        int manaCosts = (int) (manaBase * manaMult);

        compound.setInteger("manaCosts", manaCosts);
        double tier = Math.min(Math.log(Math.pow(manaCosts, 2.4)), 0.01 * Math.pow(manaCosts, 0.7));
        compound.setInteger("tier", (int) tier);
        compound.setDouble("skillXP", Math.pow(manaCosts, 0.3));
        return true;
    }
}