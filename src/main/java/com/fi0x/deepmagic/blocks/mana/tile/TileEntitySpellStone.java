package com.fi0x.deepmagic.blocks.mana.tile;

import com.fi0x.deepmagic.items.spells.effects.defensive.*;
import com.fi0x.deepmagic.items.spells.effects.offensive.*;
import com.fi0x.deepmagic.items.spells.effects.util.*;
import com.fi0x.deepmagic.items.spells.modifiers.*;
import com.fi0x.deepmagic.items.spells.types.*;
import com.fi0x.deepmagic.util.IManaTileEntity;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;

public class TileEntitySpellStone extends TileEntity implements ITickable, IInventory, IManaTileEntity
{
    private NonNullList<ItemStack> inventory = NonNullList.withSize(7, ItemStack.EMPTY);
    private String customName;

    private double requiredMana = 0;
    private int manaTick = 100;

    private final ArrayList<String> spellParts = new ArrayList<>();

    private int manaAdder;
    private double manaMultiplier;
    private double tier;

    @Override
    public void update()//TODO: Re-make
    {
        manaTick--;
        if(manaTick < -1000)
        {
            if(ConfigHandler.spellStoneExplosion)
            {
                boolean env = ConfigHandler.spellStoneEnvironment;
                world.newExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (float) consumedItems.size() / 4 + 1, false, env);
                consumedItems.clear();
                spellParts.clear();
                manaTick = 100;
            }
        }
        if(requiredMana <= ConfigHandler.manaToleranceSpellStone && manaTick < 0) manaTick += 100;

        requiredMana++;
        if(manaTick % 100 == 0)
        {
            requiredMana += consumedItems.size() * 100;
        }

        markDirty();
    }
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        compound.setDouble("requiredmana", requiredMana);
        compound.setInteger("tickdelay", manaTick);

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

        compound.setInteger("manaAdder", manaAdder);
        compound.setDouble("manaMultiplier", manaMultiplier);
        compound.setDouble("spellTier", tier);

        ItemStackHelper.saveAllItems(compound, inventory);
        if(hasCustomName()) compound.setString("customName", customName);

        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        requiredMana = compound.getDouble("requiredmana");
        manaTick = compound.getInteger("tickdelay");

        String parts = compound.getString("parts");
        spellParts.addAll(Arrays.asList(parts.split(":")));

        manaAdder = compound.getInteger("manaAdder");
        manaMultiplier = compound.getDouble("manaMultiplier");
        tier = compound.getDouble("spellTier");

        inventory = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inventory);
        if(compound.hasKey("customName")) setCustomName(compound.getString("customName"));

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

                manaMultiplier += 1;
                tier += 2;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpTyBeam.NAME);

                manaMultiplier += 0.3;
                setSpellTier(3);
                flag = true;
            } else if(consumedItems.contains("item.clock") && consumedItems.contains("item.comparator"))
            {
                spellParts.add(SpTyIterate.NAME);
                consumedItems.remove("item.clock");
                consumedItems.remove("item.comparator");

                manaMultiplier += 1;
                tier += 1;
                flag = true;
            } else if(consumedItems.contains("item.bow") && consumedItems.contains("item.arrow"))
            {
                spellParts.add(SpTyProjectile.NAME);
                consumedItems.remove("item.bow");
                consumedItems.remove("item.arrow");

                manaMultiplier += 1;
                tier += 1;
                flag = true;
            } else if(consumedItems.remove("item.magic_sigil"))
            {
                spellParts.add(SpTyRune.NAME);

                manaMultiplier += 0.5;
                tier += 3;
                flag = true;
            } else if(consumedItems.remove("item.mana_interface"))
            {
                spellParts.add(SpTySelf.NAME);

                manaAdder += 50;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpTyStream.NAME);
                consumedItems.remove(null);

                manaMultiplier += 0.3;
                setSpellTier(3);
                flag = true;
            } else if(consumedItems.remove("tile.button"))
            {
                spellParts.add(SpTyTouch.NAME);

                manaAdder += 50;
                setSpellTier(1);
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

                manaAdder += 100;
                setSpellTier(2);
                flag = true;
            } else if(consumedItems.remove("item.swordIron"))
            {
                spellParts.add(SpMoDamage.NAME);

                manaAdder += 100;
                setSpellTier(2);
                flag = true;
            } else if(consumedItems.contains("item.clock") && consumedItems.contains("item.diode"))
            {
                spellParts.add(SpMoDuration.NAME);
                consumedItems.remove("item.clock");
                consumedItems.remove("item.diode");

                manaMultiplier += 0.2;
                setSpellTier(2);
                flag = true;
            } else if(consumedItems.contains("item.shovelIron") && consumedItems.contains("item.pickaxeIron") && consumedItems.contains("item.hatchetIron"))
            {
                spellParts.add(SpMoEnvironmental.NAME);
                consumedItems.remove("item.shovelIron");
                consumedItems.remove("item.pickaxeIron");
                consumedItems.remove("item.hatchetIron");

                manaMultiplier += 0.5;
                setSpellTier(4);
                flag = true;
            } else if(consumedItems.contains("tile.blockLapis") && consumedItems.contains("item.diamond"))
            {
                spellParts.add(SpMoFortune.NAME);
                consumedItems.remove("tile.blockLapis");
                consumedItems.remove("item.diamond");

                manaMultiplier += 0.2;
                setSpellTier(2);
                flag = true;
            } else if(consumedItems.remove("tile.obsidian"))
            {
                spellParts.add(SpMoGravity.NAME);

                manaAdder += 20;
                setSpellTier(2);
                flag = true;
            } else if(consumedItems.remove("item.appleGold"))
            {
                spellParts.add(SpMoHealPower.NAME);

                manaMultiplier += 0.2;
                setSpellTier(2);
                flag = true;
            } else if(consumedItems.contains("tile.blockLapis") && consumedItems.contains("item.diamond"))
            {
                spellParts.add(SpMoLooting.NAME);
                consumedItems.remove("tile.blockLapis");
                consumedItems.remove("item.diamond");

                manaMultiplier += 0.2;
                setSpellTier(2);
                flag = true;
            } else if(consumedItems.remove("item.swordDiamond"))
            {
                spellParts.add(SpMoPiercing.NAME);

                manaMultiplier += 0.5;
                setSpellTier(2);
                flag = true;
            } else if(consumedItems.contains("item.coal") && consumedItems.contains("item.ingotIron") && consumedItems.contains("item.ingotGold") && consumedItems.contains("item.diamond"))
            {
                spellParts.add(SpMoPower.NAME);
                consumedItems.remove("item.coal");
                consumedItems.remove("item.ingotIron");
                consumedItems.remove("item.ingotGold");
                consumedItems.remove("item.diamond");

                manaAdder += 50;
                setSpellTier(2);
                flag = true;
            } else if(consumedItems.remove("item.compass"))
            {
                spellParts.add(SpMoRadius.NAME);
                consumedItems.remove(null);

                manaAdder += 50;
                setSpellTier(2);
                flag = true;
            } else if(consumedItems.remove("item.arrow"))
            {
                spellParts.add(SpMoRange.NAME);

                manaAdder += 50;
                setSpellTier(2);
                flag = true;
            } else if(consumedItems.remove("item.slimeball"))
            {
                spellParts.add(SpMoRicochet.NAME);

                manaAdder += 50;
                setSpellTier(2);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpMoSilkTouch.NAME);
                consumedItems.remove(null);

                manaMultiplier += 0.5;
                setSpellTier(4);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpMoSplit.NAME);
                consumedItems.remove(null);

                manaMultiplier += 0.3;
                setSpellTier(4);
                flag = true;
            } else if(consumedItems.contains("item.clock") && consumedItems.contains("item.magic_sigil"))
            {
                spellParts.add(SpMoTickSpeed.NAME);

                manaAdder += 100;
                setSpellTier(4);
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

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfFeatherFall.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfHaste.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfHeal.NAME);
                consumedItems.remove(null);

                manaAdder += 50;
                tier += 1;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfManaShield.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfMiningSpeed.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfMirrorShield.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfRegeneration.NAME);
                consumedItems.remove(null);

                manaAdder += 40;
                tier += 1;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfRoot.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfSlowness.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfWall.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfWither.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfAccelerate.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfBlindness.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfDrown.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(consumedItems.remove("tile.tnt"))
            {
                spellParts.add(SpEfExplosion.NAME);

                manaAdder += 50;
                setSpellTier(2);
                flag = true;
            } else if(consumedItems.remove("tile.magma"))
            {
                spellParts.add(SpEfFireDamage.NAME);

                manaAdder += 30;
                setSpellTier(2);
                flag = true;
            } else if(consumedItems.remove("tile.ice"))
            {
                spellParts.add(SpEfFrostDamage.NAME);

                manaAdder += 30;
                setSpellTier(2);
                flag = true;
            } else if(consumedItems.remove("item.flintAndSteel"))
            {
                spellParts.add(SpEfIgnition.NAME);

                manaAdder += 30;
                flag = true;
            } else if(consumedItems.remove("tile.pistonBase"))
            {
                spellParts.add(SpEfKnockback.NAME);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfMagicDamage.NAME);
                consumedItems.remove(null);

                manaAdder += 40;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfMidnight.NAME);
                consumedItems.remove(null);

                manaAdder += 100;
                tier += 1;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfPhysicalDamage.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfPoison.NAME);
                consumedItems.remove(null);

                manaAdder += 30;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfSink.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfStun.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfAge.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfBind.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(consumedItems.remove("item.enderPearl"))
            {
                spellParts.add(SpEfBlink.NAME);

                manaAdder += 50;
                setSpellTier(2);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfBuff.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(consumedItems.remove("item.cookie"))
            {
                spellParts.add(SpEfCookie.NAME);

                manaAdder += 20;
                setSpellTier(5);
                flag = true;
            } else if(consumedItems.contains("item.wheat") && consumedItems.contains("item.carrots") && consumedItems.contains("item.seeds"))
            {
                spellParts.add(SpEfCharm.NAME);
                consumedItems.remove("item.wheat");
                consumedItems.remove("item.carrot");
                consumedItems.remove("item.wheat_seeds");

                manaAdder += 40;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfDayNight.NAME);
                consumedItems.remove(null);

                manaAdder += 100;
                setSpellTier(4);
                flag = true;
            } else if(consumedItems.contains("item.iron_shovel") && consumedItems.contains("item.iron_pickaxe") && consumedItems.contains("item.iron_axe"))
            {
                spellParts.add(SpEfDig.NAME);
                consumedItems.remove("item.iron_shovel");
                consumedItems.remove("item.iron_pickaxe");
                consumedItems.remove("item.iron_axe");

                manaAdder += 20;
                flag = true;
            } else if(consumedItems.remove("item.eyeOfEnder"))
            {
                spellParts.add(SpEfDimensionalTeleport.NAME);

                manaAdder += 50;
                setSpellTier(3);
                flag = true;
            } else if(consumedItems.remove("item.bucket"))
            {
                spellParts.add(SpEfDry.NAME);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfExchange.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfFly.NAME);
                consumedItems.remove(null);

                manaAdder += 50;
                tier += 1;
                flag = true;
            } else if(consumedItems.remove("tile.snow"))
            {
                spellParts.add(SpEfFreeze.NAME);
                consumedItems.remove(null);

                manaAdder += 30;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfGrowth.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfLevitate.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfLifeLink.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                tier += 1;
                flag = true;
            } else if(consumedItems.remove("tile.lightgem"))
            {
                spellParts.add(SpEfLight.NAME);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfLightning.NAME);
                consumedItems.remove(null);

                manaAdder += 50;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfMagnet.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfManaLink.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                tier += 1;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfMarkLocation.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfPlace.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(consumedItems.remove("item.bucketWater"))
            {
                spellParts.add(SpEfRain.NAME);

                manaAdder += 50;
                setSpellTier(4);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfRepel.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfSmelt.NAME);
                consumedItems.remove(null);

                manaAdder += 50;
                flag = true;
            } else if(consumedItems.remove("tile.chest"))
            {
                spellParts.add(SpEfStore.NAME);

                manaAdder += 20;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfStorm.NAME);
                consumedItems.remove(null);

                manaAdder += 80;
                setSpellTier(4);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfSummon.NAME);
                consumedItems.remove(null);

                manaAdder += 100;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfSunshine.NAME);
                consumedItems.remove(null);

                manaAdder += 80;
                setSpellTier(4);
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfSwimSpeed.NAME);
                consumedItems.remove(null);

                manaAdder += 20;
                flag = true;
            } else if(consumedItems.remove("item.teleportation_crystal"))
            {
                spellParts.add(SpEfTeleport.NAME);

                manaAdder += 50;
                tier += 2;
                flag = true;
            } else if(false)
            {
                spellParts.add(SpEfTotalRecall.NAME);
                consumedItems.remove(null);

                manaAdder += 100;
                setSpellTier(5);
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
        markDirty();
    }
    public int getManaAdder()
    {
        return manaAdder;
    }
    public void resetManaAdder()
    {
        manaAdder = 0;
        markDirty();
    }
    public double getManaMultiplier()
    {
        return manaMultiplier;
    }
    public void resetManaMultiplier()
    {
        manaMultiplier = 0;
        markDirty();
    }
    private void setSpellTier(double newTier)
    {
        if(newTier > tier) tier = newTier;
        else tier += newTier / tier;
        markDirty();
    }
    public double getSpellTier()
    {
        return tier;
    }
    public void resetSpellTier()
    {
        tier = 0;
        markDirty();
    }
    @Override
    public double getSpaceForMana()
    {
        return requiredMana;
    }
    @Override
    public double addManaToStorage(double amount)
    {
        double ret = amount - requiredMana;
        if(ret > 0) requiredMana = 0;
        else requiredMana -= amount;
        markDirty();
        return ret > 0 ? ret : 0;
    }
    @Override
    public int getSizeInventory()
    {
        return inventory.size();
    }
    @Override
    public boolean isEmpty()
    {
        for(ItemStack stack : inventory)
        {
            if(!stack.isEmpty()) return false;
        }
        return true;
    }
    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index)
    {
        return inventory.get(index);
    }
    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(inventory, index, count);
    }
    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(inventory, index);
    }
    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack)
    {
        inventory.set(index, stack);

        if(stack.getCount() > getInventoryStackLimit()) stack.setCount(getInventoryStackLimit());
    }
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }
    @Override
    public boolean isUsableByPlayer(@Nonnull EntityPlayer player)
    {
        return world.getTileEntity(pos) == this && player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64;
    }
    @Override
    public void openInventory(@Nonnull EntityPlayer player)
    {
    }
    @Override
    public void closeInventory(@Nonnull EntityPlayer player)
    {
    }
    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack)
    {
        return index != 1;
    }
    @Override
    public int getField(int id)
    {
        return 0;
    }
    @Override
    public void setField(int id, int value)
    {

    }
    @Override
    public int getFieldCount()
    {
        return 0;
    }
    @Override
    public void clear()
    {
        inventory.clear();
    }
    @Nonnull
    @Override
    public String getName()
    {
        return hasCustomName() ? customName : "container.spell_stone";
    }
    @Override
    public boolean hasCustomName()
    {
        return customName != null && !customName.isEmpty();
    }
    @Nonnull
    @Override
    public ITextComponent getDisplayName()
    {
        return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName());
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }
}
