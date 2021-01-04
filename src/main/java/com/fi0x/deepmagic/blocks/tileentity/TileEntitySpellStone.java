package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.items.spells.effects.defensive.*;
import com.fi0x.deepmagic.items.spells.effects.offensive.*;
import com.fi0x.deepmagic.items.spells.effects.util.*;
import com.fi0x.deepmagic.items.spells.modifiers.*;
import com.fi0x.deepmagic.items.spells.types.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.*;
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
            String name;
            if(i.getItem().getItem() instanceof ItemBlock) name = i.getItem().getUnlocalizedName();
            else if(i.getItem().getItem() instanceof ItemBucket) name = i.getItem().getUnlocalizedName();
            else name = i.getItem().getItem().getClass().getName();
            for(int j = 0; j < i.getItem().getCount(); j++)
            {
                consumedItems.add(name);
            }
            world.removeEntity(i);
            markDirty();
        }
        updateSpellTypes();
        updateSpellModifiers();
        updateSpellEffects();
    }
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        StringBuilder parts = new StringBuilder();
        parts.append(spellParts.get(0));
        for(int i = 1; i < spellParts.size(); i++)
        {
            parts.append(":").append(spellParts.get(i));
        }
        compound.setString("parts", parts.toString());

        StringBuilder items = new StringBuilder();
        items.append(consumedItems.get(0));
        for(int i = 1; i < consumedItems.size(); i++)
        {
            items.append("_:_").append(consumedItems.get(i));
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

        if(consumedItems.contains(ItemSplashPotion.class.getName()))
        {
            spellParts.add(SpTyAreaOfEffect.NAME);
            consumedItems.remove(ItemSplashPotion.class.getName());
            flag = true;
        }
        //TODO: Add fitting item combinations
        if(false)
        {
            spellParts.add(SpTyBeam.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpTyIterate.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(consumedItems.contains(ItemSnowball.class.getName()))
        {
            spellParts.add(SpTyProjectile.NAME);
            consumedItems.remove(ItemSnowball.class.getName());
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpTyRune.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(consumedItems.contains(ItemGlassBottle.class.getName()))
        {
            spellParts.add(SpTySelf.NAME);
            consumedItems.remove(ItemGlassBottle.class.getName());
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpTyStream.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpTyTouch.NAME);
            consumedItems.remove(null);
            flag = true;
        }

        if(flag) markDirty();
    }
    private void updateSpellModifiers()
    {
        boolean flag = false;

        if(consumedItems.contains("item.bucketLava"))
        {
            spellParts.add(SpMoAutoSmelt.NAME);
            consumedItems.remove("item.bucketLava");
            flag = true;
        }
        //TODO: Add fitting item combinations
        if(false)
        {
            spellParts.add(SpMoDamage.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(consumedItems.contains(ItemClock.class.getName()))
        {
            spellParts.add(SpMoDuration.NAME);
            consumedItems.remove(ItemClock.class.getName());
            flag = true;
        }
        if(consumedItems.contains(ItemFlintAndSteel.class.getName()))
        {
            spellParts.add(SpMoEnvironmental.NAME);
            consumedItems.remove(ItemFlintAndSteel.class.getName());
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpMoFortune.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpMoGravity.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpMoHealPower.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpMoLooting.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpMoPiercing.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpMoPower.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpMoRadius.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(consumedItems.contains(ItemArrow.class.getName()))
        {
            spellParts.add(SpMoRange.NAME);
            consumedItems.remove(ItemArrow.class.getName());
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpMoRicochet.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpMoSilkTouch.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpMoSplit.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpMoTickSpeed.NAME);
            consumedItems.remove(null);
            flag = true;
        }

        if(flag) markDirty();
    }
    private void updateSpellEffects()
    {
        boolean flag = false;

        if(consumedItems.contains(ItemShield.class.getName()))
        {
            spellParts.add(SpEfDeflect.NAME);
            consumedItems.remove(ItemShield.class.getName());
            flag = true;
        }
        //TODO: Add fitting item combinations
        if(false)
        {
            spellParts.add(SpEfFeatherFall.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfHaste.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfHeal.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfManaShield.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfMiningSpeed.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfMirrorShield.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfRegeneration.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfRoot.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfSlowness.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfWall.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfWither.NAME);
            consumedItems.remove(null);
            flag = true;
        }

        if(false)
        {
            spellParts.add(SpEfAccelerate.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfBlindness.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfDrown.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(consumedItems.contains("tile.tnt"))
        {
            spellParts.add(SpEfExplosion.NAME);
            consumedItems.remove("tile.tnt");
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfFireDamage.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfFrostDamage.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfIgnition.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfKnockback.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfMagicDamage.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfMidnight.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfPhysicalDamage.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfPoison.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfSink.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfStun.NAME);
            consumedItems.remove(null);
            flag = true;
        }

        if(false)
        {
            spellParts.add(SpEfAge.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfBind.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(consumedItems.contains(ItemEnderPearl.class.getName()))
        {
            spellParts.add(SpEfBlink.NAME);
            consumedItems.remove(ItemEnderPearl.class.getName());
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfBuff.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfCharm.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfDayNight.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfDig.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfDimensionalTeleport.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(consumedItems.contains("item.bucket"))
        {
            spellParts.add(SpEfDry.NAME);
            consumedItems.remove("item.bucket");
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfExchange.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfFly.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfFreeze.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfGrowth.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfLevitate.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfLifeLink.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfLight.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfLightning.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfMagnet.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfManaLink.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfMarkLocation.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfPlace.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(consumedItems.contains("item.bucketWater"))
        {
            spellParts.add(SpEfRain.NAME);
            consumedItems.remove("item.bucketWater");
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfRepel.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfSmelt.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfStore.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfStorm.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfSummon.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfSunshine.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfSwimSpeed.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfTeleport.NAME);
            consumedItems.remove(null);
            flag = true;
        }
        if(false)
        {
            spellParts.add(SpEfTotalRecall.NAME);
            consumedItems.remove(null);
            flag = true;
        }

        if(flag) markDirty();
    }

    public String getSpellParts()
    {
        StringBuilder parts = new StringBuilder();
        parts.append(spellParts.get(0));
        for(int i = 1; i < spellParts.size(); i++)
        {
            parts.append(":").append(spellParts.get(i));
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
