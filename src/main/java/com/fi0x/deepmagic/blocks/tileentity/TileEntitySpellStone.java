package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.items.spells.effects.defensive.*;
import com.fi0x.deepmagic.items.spells.effects.offensive.*;
import com.fi0x.deepmagic.items.spells.effects.util.*;
import com.fi0x.deepmagic.items.spells.modifiers.*;
import com.fi0x.deepmagic.items.spells.types.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileEntitySpellStone extends TileEntity implements ITickable
{
    private final ArrayList<String> spellParts = new ArrayList<>();
    private final ArrayList<String> consumedItems = new ArrayList<>();

    private int manaAdder;
    private double manaMultiplier;

    @Override
    public void update()
    {
        AxisAlignedBB aabb = new AxisAlignedBB(new BlockPos(this.pos.getX(), this.pos.getY() + 1, this.pos.getZ()));
        List<EntityItem> itemEntities = world.getEntitiesWithinAABB(EntityItem.class, aabb);

        for(EntityItem i : itemEntities)
        {
            String name = i.getItem().getUnlocalizedName();
            for(int j = 0; j < i.getItem().getCount(); j++)
            {
                consumedItems.add(name);
            }
            world.removeEntity(i);
            markDirty();
        }
        if(!itemEntities.isEmpty())
        {
            updateSpellTypes();
            updateSpellModifiers();
            updateSpellEffects();
        }
    }
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        StringBuilder parts = new StringBuilder();
        if(!spellParts.isEmpty())
        {
            parts.append(spellParts.get(0));
            for(int i = 1; i < spellParts.size(); i++)
            {
                parts.append(":").append(spellParts.get(i));
            }
        }
        compound.setString("parts", parts.toString());

        StringBuilder items = new StringBuilder();
        if(!consumedItems.isEmpty())
        {
            items.append(consumedItems.get(0));
            for(int i = 1; i < consumedItems.size(); i++)
            {
                items.append("_:_").append(consumedItems.get(i));
            }
        }
        compound.setString("items", items.toString());

        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        String parts = compound.getString("parts");
        spellParts.addAll(Arrays.asList(parts.split(":")));

        String items = compound.getString("items");
        consumedItems.addAll(Arrays.asList(items.split("_:_")));

        super.readFromNBT(compound);
    }

    private void updateSpellTypes()
    {
        boolean flag = false;

        //TODO: Add missing item combinations
        while(consumedItems.remove("item.magic_flow_controller"))
        {
            if(consumedItems.remove("item.splash_potion"))
            {
                spellParts.add(SpTyAreaOfEffect.NAME);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpTyBeam.NAME);
                flag = true;
            } else if(consumedItems.contains("item.clock") && consumedItems.contains("item.comparator"))
            {
                spellParts.add(SpTyIterate.NAME);
                consumedItems.remove("item.clock");
                consumedItems.remove("item.comparator");
                flag = true;
            } else if(consumedItems.contains("item.bow") && consumedItems.contains("item.arrow"))
            {
                spellParts.add(SpTyProjectile.NAME);
                consumedItems.remove("item.bow");
                consumedItems.remove("item.arrow");
                flag = true;
            } else if(consumedItems.remove("item.magic_sigil"))
            {
                spellParts.add(SpTyRune.NAME);
                flag = true;
            } else if(consumedItems.remove("item.mana_interface"))
            {
                spellParts.add(SpTySelf.NAME);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpTyStream.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(consumedItems.remove("tile.button"))
            {
                spellParts.add(SpTyTouch.NAME);
                flag = true;
            } else
            {
                consumedItems.add("item.magic_flow_controller");
                break;
            }
        }

        if(flag) markDirty();
    }
    private void updateSpellModifiers()
    {
        boolean flag = false;

        //TODO: Add missing item combinations
        while(consumedItems.remove("item.magic_powder"))
        {
            if(consumedItems.remove("item.bucketLava"))
            {
                spellParts.add(SpMoAutoSmelt.NAME);
                flag = true;
            } else if(consumedItems.remove("item.swordIron"))
            {
                spellParts.add(SpMoDamage.NAME);
                flag = true;
            } else if(consumedItems.contains("item.clock") && consumedItems.contains("item.diode"))
            {
                spellParts.add(SpMoDuration.NAME);
                consumedItems.remove("item.clock");
                consumedItems.remove("item.diode");
                flag = true;
            } else if(consumedItems.contains("item.shovelIron") && consumedItems.contains("item.pickaxeIron") && consumedItems.contains("item.hatchetIron"))
            {
                spellParts.add(SpMoEnvironmental.NAME);
                consumedItems.remove("item.shovelIron");
                consumedItems.remove("item.pickaxeIron");
                consumedItems.remove("item.hatchetIron");
                flag = true;
            } else if(consumedItems.contains("tile.blockLapis") && consumedItems.contains("item.diamond"))
            {
                spellParts.add(SpMoFortune.NAME);
                consumedItems.remove("tile.blockLapis");
                consumedItems.remove("item.diamond");
                flag = true;
            } else if(consumedItems.remove("tile.obsidian"))
            {
                spellParts.add(SpMoGravity.NAME);
                flag = true;
            } else if(consumedItems.remove("item.appleGold"))
            {
                spellParts.add(SpMoHealPower.NAME);
                flag = true;
            } else if(consumedItems.contains("tile.blockLapis") && consumedItems.contains("item.diamond"))
            {
                spellParts.add(SpMoLooting.NAME);
                consumedItems.remove("tile.blockLapis");
                consumedItems.remove("item.diamond");
                flag = true;
            } else if(consumedItems.remove("item.swordDiamond"))
            {
                spellParts.add(SpMoPiercing.NAME);
                flag = true;
            } else if(consumedItems.contains("item.coal") && consumedItems.contains("item.ingotIron") && consumedItems.contains("item.ingotGold") && consumedItems.contains("item.diamond"))
            {
                spellParts.add(SpMoPower.NAME);
                consumedItems.remove("item.coal");
                consumedItems.remove("item.ingotIron");
                consumedItems.remove("item.ingotGold");
                consumedItems.remove("item.diamond");
                flag = true;
            } else if(consumedItems.remove("item.compass"))
            {
                spellParts.add(SpMoRadius.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(consumedItems.remove("item.arrow"))
            {
                spellParts.add(SpMoRange.NAME);
                flag = true;
            } else if(consumedItems.remove("item.slimeball"))
            {
                spellParts.add(SpMoRicochet.NAME);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpMoSilkTouch.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpMoSplit.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(consumedItems.contains("item.clock") && consumedItems.contains("item.magic_sigil"))
            {
                spellParts.add(SpMoTickSpeed.NAME);
                flag = true;
            } else
            {
                consumedItems.add("item.magic_powder");
                break;
            }
        }

        if(flag) markDirty();
    }
    private void updateSpellEffects()
    {
        boolean flag = false;

        //TODO: Add missing item combinations
        while(consumedItems.remove("item.magic_converter"))
        {
            if(consumedItems.remove("item.shield"))
            {
                spellParts.add(SpEfDeflect.NAME);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfFeatherFall.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfHaste.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfHeal.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfManaShield.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfMiningSpeed.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfMirrorShield.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfRegeneration.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfRoot.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfSlowness.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfWall.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfWither.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfAccelerate.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfBlindness.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfDrown.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(consumedItems.remove("tile.tnt"))
            {
                spellParts.add(SpEfExplosion.NAME);
                flag = true;
            } else if(consumedItems.remove("tile.magma"))
            {
                spellParts.add(SpEfFireDamage.NAME);
                flag = true;
            } else if(consumedItems.remove("tile.ice"))
            {
                spellParts.add(SpEfFrostDamage.NAME);
                flag = true;
            } else if(consumedItems.remove("item.flintAndSteel"))
            {
                spellParts.add(SpEfIgnition.NAME);
                flag = true;
            } else if(consumedItems.remove("tile.pistonBase"))
            {
                spellParts.add(SpEfKnockback.NAME);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfMagicDamage.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfMidnight.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfPhysicalDamage.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfPoison.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfSink.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfStun.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfAge.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfBind.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(consumedItems.remove("item.enderPearl"))
            {
                spellParts.add(SpEfBlink.NAME);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfBuff.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfCharm.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfDayNight.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfDig.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(consumedItems.remove("item.eyeOfEnder"))
            {
                spellParts.add(SpEfDimensionalTeleport.NAME);
                flag = true;
            } else if(consumedItems.remove("item.bucket"))
            {
                spellParts.add(SpEfDry.NAME);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfExchange.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfFly.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(consumedItems.remove("tile.snow"))
            {
                spellParts.add(SpEfFreeze.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfGrowth.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfLevitate.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfLifeLink.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(consumedItems.remove("tile.lightgem"))
            {
                spellParts.add(SpEfLight.NAME);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfLightning.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfMagnet.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfManaLink.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfMarkLocation.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfPlace.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(consumedItems.remove("item.bucketWater"))
            {
                spellParts.add(SpEfRain.NAME);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfRepel.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfSmelt.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(consumedItems.remove("tile.chest"))
            {
                spellParts.add(SpEfStore.NAME);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfStorm.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfSummon.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfSunshine.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfSwimSpeed.NAME);
                consumedItems.remove(null);
                flag = true;
            } else if(consumedItems.remove("item.teleportation_crystal"))
            {
                spellParts.add(SpEfTeleport.NAME);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfTotalRecall.NAME);
                consumedItems.remove(null);
                flag = true;
            } else
            {
                consumedItems.add("item.magic_converter");
                break;
            }
        }

        if(flag) markDirty();
    }

    public int getPartCount()
    {
        return spellParts.size();
    }
    public String getSpellParts()
    {
        StringBuilder parts = new StringBuilder();
        if(!spellParts.isEmpty())
        {
            parts.append(spellParts.get(0));
            for(int i = 1; i < spellParts.size(); i++)
            {
                parts.append(":").append(spellParts.get(i));
            }
        }
        return parts.toString();
    }
    public void resetParts()
    {
        spellParts.clear();
        if(!consumedItems.isEmpty())
        {
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3, false);
            consumedItems.clear();
        }
        manaMultiplier = 1;
        markDirty();
    }
    public int getManaAdder()
    {
        return manaAdder;
    }
    public void resetManaAdder()
    {
        manaAdder = 0;
    }
    public double getManaMultiplier()
    {
        return manaMultiplier;
    }
    public void resetManaMultiplier()
    {
        manaMultiplier = 0;
    }
}
