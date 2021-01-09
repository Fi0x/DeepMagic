package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.blocks.mana.ManaGeneratorMob;
import com.fi0x.deepmagic.util.IManaTileEntity;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import java.util.List;

public class TileEntityManaGeneratorMob extends TileEntity implements IInventory, ITickable, IManaTileEntity
{
    private BlockPos manaTargetPos;
    private String customName;

    private TileEntity linkedTE;
    private int cooldown;
    private int storedMana;

    @Override
    public int getSizeInventory()
    {
        return 0;
    }
    @Override
    public boolean isEmpty()
    {
        return true;
    }
    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index)
    {
        return ItemStack.EMPTY;
    }
    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStack.EMPTY;
    }
    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStack.EMPTY;
    }
    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack)
    {
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
        return true;
    }
    @Override
    public int getField(int id)
    {
        switch (id)
        {
            case 0: return cooldown;
            case 1: return storedMana;
        }
        return 0;
    }
    @Override
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0: cooldown = value;
            break;
            case 1: storedMana = value;
            break;
        }
    }
    @Override
    public int getFieldCount()
    {
        return 2;
    }
    @Override
    public void clear()
    {
    }
    @Override
    public void update()
    {
        boolean wasRunning = isRunning();
        boolean dirty = false;
        if(isRunning())
        {
            cooldown--;
            dirty = true;
        }
        if(world.isRemote) return;

        if(storedMana < ConfigHandler.manaGeneratorManaCapacity && cooldown <= 10)
        {
            int mobRange = ConfigHandler.manaGeneratorMobRange;
            AxisAlignedBB area = new AxisAlignedBB(pos.getX()-mobRange, pos.getY()-mobRange, pos.getZ()-mobRange, pos.getX()+mobRange, pos.getY()+mobRange, pos.getZ()+mobRange);
            List<EntityMob> entities = world.getEntitiesWithinAABB(EntityMob.class, area);
            if(!entities.isEmpty())
            {
                cooldown = 30;
                int gain = ConfigHandler.manaGainFromMob;

                for(EntityMob entity : entities)
                {
                    entity.attackEntityFrom(DamageSource.MAGIC, 1);
                    storedMana += gain;
                }
                dirty = true;
            }
        }

        if(isRunning() != wasRunning)
        {
            ManaGeneratorMob.setState(isRunning(), world, pos);
            dirty = true;
        }
        if(storedMana >= 100)
        {
            double sent = ManaHelper.sendMana(world, pos, manaTargetPos, linkedTE, ConfigHandler.manaBlockTransferRange, storedMana);
            if(sent > 0)
            {
                storedMana -= (int) sent;
                dirty = true;
            }
        }
        if(dirty) markDirty();
    }
    @Nonnull
    @Override
    public String getName()
    {
        return hasCustomName() ? customName : "container.mana_generator_mob";
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
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        if(manaTargetPos != null)
        {
            NBTTagCompound position = new NBTTagCompound();
            position.setInteger("x", manaTargetPos.getX());
            position.setInteger("y", manaTargetPos.getY());
            position.setInteger("z", manaTargetPos.getZ());
            compound.setTag("target", position);
        }

        compound.setInteger("cooldown", cooldown);
        compound.setInteger("storedMana", storedMana);
        if(hasCustomName()) compound.setString("customName", customName);
        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        if(compound.hasKey("target"))
        {
            NBTTagCompound position = compound.getCompoundTag("target");
            int x = position.getInteger("x");
            int y = position.getInteger("y");
            int z = position.getInteger("z");
            manaTargetPos = new BlockPos(x, y, z);
        }

        cooldown = compound.getInteger("cooldown");
        storedMana = compound.getInteger("storedMana");
        if(compound.hasKey("customName")) setCustomName(compound.getString("customName"));
        super.readFromNBT(compound);
    }
    public void setCustomName(String customName)
    {
        this.customName = customName;
    }
    public boolean isRunning()
    {
        return cooldown > 0;
    }
    public void setManaTargetPos(BlockPos blockPos)
    {
        manaTargetPos = blockPos;
        if(manaTargetPos == null) linkedTE = null;
        else linkedTE = world.getTileEntity(manaTargetPos);
    }
}
