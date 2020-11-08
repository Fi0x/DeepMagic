package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.blocks.mana.ManaGeneratorMob;
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

public class TileEntityManaGeneratorMob extends TileEntity implements IInventory, ITickable
{
    private String customName;

    private BlockPos linkedAltarPos;
    private TileEntityManaAltar linkedAltar;
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
            if(sendManaToAltar()) dirty = true;
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
        if(linkedAltarPos != null)
        {
            compound.setInteger("altarX", linkedAltarPos.getX());
            compound.setInteger("altarY", linkedAltarPos.getY());
            compound.setInteger("altarZ", linkedAltarPos.getZ());
            compound.setBoolean("linked", true);
        } else compound.setBoolean("linked", false);

        compound.setInteger("cooldown", cooldown);
        compound.setInteger("storedMana", storedMana);
        if(hasCustomName()) compound.setString("customName", customName);
        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        if(compound.hasKey("linked") && compound.getBoolean("linked"))
        {
            int x = compound.getInteger("altarX");
            int y = compound.getInteger("altarY");
            int z = compound.getInteger("altarZ");
            linkedAltarPos = new BlockPos(x, y, z);
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
    public void setLinkedAltarPos(BlockPos blockPos)
    {
        linkedAltarPos = blockPos;
        if(linkedAltarPos == null) linkedAltar = null;
        else linkedAltar = (TileEntityManaAltar) world.getTileEntity(linkedAltarPos);
    }
    private boolean sendManaToAltar()
    {
        if(!ManaHelper.isAltarValid(world, pos, linkedAltarPos, linkedAltar)) return false;
        linkedAltar = (TileEntityManaAltar) world.getTileEntity(linkedAltarPos);

        int spaceInAltar = (int) linkedAltar.getSpaceInAltar();
        if(spaceInAltar > storedMana)
        {
            if(linkedAltar.addManaToStorage(storedMana)) storedMana = 0;
        } else
        {
            if(linkedAltar.addManaToStorage(spaceInAltar)) storedMana -= spaceInAltar;
        }
        return true;
    }
}
