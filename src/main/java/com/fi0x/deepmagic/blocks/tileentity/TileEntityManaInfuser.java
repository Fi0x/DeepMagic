package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.blocks.mana.ManaInfuser;
import com.fi0x.deepmagic.util.IManaTileEntity;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.util.recipes.ManaInfuserRecipes;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;

public class TileEntityManaInfuser extends TileEntity implements IInventory, ITickable, IManaTileEntity
{
    private NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);
    private String customName;

    private int infusionProgress;
    private int totalInfusionTime;
    private int storedMana;

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
        switch (id)
        {
            case 0: return infusionProgress;
            case 1: return totalInfusionTime;
            case 2: return storedMana;
        }
        return 0;
    }
    @Override
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0: infusionProgress = value;
            break;
            case 1: totalInfusionTime = value;
            break;
            case 2: storedMana = value;
            break;
        }
    }
    @Override
    public int getFieldCount()
    {
        return 3;
    }
    @Override
    public void clear()
    {
        inventory.clear();
    }
    @Override
    public void update()
    {
        boolean wasRunning = isRunning();
        boolean dirty = false;

        if(!world.isRemote)
        {
            ItemStack stack = inventory.get(0);
            if(stack.isEmpty())
            {
                if(totalInfusionTime > 0)
                {
                    infusionProgress = 0;
                    totalInfusionTime = 0;
                    dirty = true;
                }
            } else if (storedMana > 0)
            {
                if(canInfuse())
                {
                    storedMana--;
                    infusionProgress++;
                    dirty = true;
                    if(totalInfusionTime == 0) totalInfusionTime = getItemInfusionTime(stack);
                    if (infusionProgress >= totalInfusionTime)
                    {
                        infusionProgress = 0;
                        infuseItem();
                        totalInfusionTime = getItemInfusionTime(stack);
                    }
                } else infusionProgress = 0;
            }

            if(isRunning() != wasRunning)
            {
                ManaInfuser.setState(isRunning(), world, pos);
                dirty = true;
            }
        }
        if(dirty) markDirty();
    }
    @Nonnull
    @Override
    public String getName()
    {
        return hasCustomName() ? customName : "container.mana_infuser";
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
        compound.setInteger("infusionProgress", infusionProgress);
        compound.setInteger("storedMana", storedMana);
        ItemStackHelper.saveAllItems(compound, inventory);
        if(hasCustomName()) compound.setString("customName", customName);
        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        infusionProgress = compound.getInteger("infusionProgress");
        totalInfusionTime = getItemInfusionTime(inventory.get(0));
        storedMana = compound.getInteger("storedMana");
        inventory = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inventory);
        if(compound.hasKey("customName")) setCustomName(compound.getString("customName"));
        super.readFromNBT(compound);
    }
    @Override
    public double getSpaceForMana()
    {
        return ConfigHandler.manaMachineManaCapacity - storedMana;
    }
    @Override
    public double addManaToStorage(double amount)
    {
        double ret = amount - (ConfigHandler.manaMachineManaCapacity - storedMana);
        if(ret > 0) storedMana = ConfigHandler.manaMachineManaCapacity;
        else storedMana += amount;
        markDirty();
        return ret > 0 ? ret : 0;
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }
    public boolean isRunning()
    {
        return infusionProgress > 0;
    }
    private boolean canInfuse()
    {
        ItemStack inputStack = inventory.get(0);
        if (inputStack.isEmpty()) return false;
        else
        {
            ItemStack infusionResult = ManaInfuserRecipes.instance().getInfuserResult(inputStack);
            if (infusionResult.isEmpty()) return false;

            ItemStack output = inventory.get(1);
            if (output.isEmpty()) return true;
            if (!output.isItemEqual(infusionResult)) return false;
            return output.getCount() + infusionResult.getCount() <= getInventoryStackLimit() && output.getCount() + infusionResult.getCount() <= output.getMaxStackSize();
        }
    }
    public static int getItemInfusionTime(ItemStack infusionStack)
    {
        if(infusionStack.isEmpty()) return 0;

        Item item = infusionStack.getItem();
        if(item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR)
        {
            return 1000;
        }
        return 120;
    }
    public static boolean isItemInfusable(ItemStack item)
    {
        return getItemInfusionTime(item) > 0;
    }
    private void infuseItem()
    {
        ItemStack input = inventory.get(0);
        ItemStack result = ManaInfuserRecipes.instance().getInfuserResult(input);
        if(!result.isEmpty())
        {
            ItemStack output = inventory.get(1);

            if(output.isEmpty()) inventory.set(1, result);
            else if(output.getItem() == result.getItem()) output.grow(result.getCount());

            input.shrink(1);
        }
    }
}
