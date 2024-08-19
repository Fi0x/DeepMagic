package com.fi0x.deepmagic.blocks.mana.tile;

import com.fi0x.deepmagic.blocks.mana.ManaGrinder;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.util.IManaTileEntity;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.util.recipes.ManaGrinderRecipes;
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

public class TileEntityManaGrinder extends TileEntity implements IInventory, ITickable, IManaTileEntity
{
    private NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    private String customName;

    private int grindProgress;
    private int totalGrindTime;
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
        return index == 0 || index > 3;
    }
    @Override
    public int getField(int id)
    {
        switch (id)
        {
            case 0: return grindProgress;
            case 1: return totalGrindTime;
            case 2: return storedMana;
        }
        return 0;
    }
    @Override
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0: grindProgress = value;
            break;
            case 1: totalGrindTime = value;
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
                if(totalGrindTime > 0)
                {
                    grindProgress = 0;
                    totalGrindTime = 0;
                    dirty = true;
                }
            } else if (storedMana > 0)
            {
                if(canGrind())
                {
                    storedMana--;
                    grindProgress++;
                    dirty = true;
                    if(totalGrindTime == 0) totalGrindTime = getItemGrindTime(stack);
                    if (grindProgress >= totalGrindTime)
                    {
                        grindProgress = 0;
                        grindItem();
                        totalGrindTime = getItemGrindTime(stack);
                    }
                } else grindProgress = 0;
            }

            if(isRunning() != wasRunning)
            {
                ManaGrinder.setState(isRunning(), world, pos);
                dirty = true;
            }
        }
        if(dirty) markDirty();
    }
    @Nonnull
    @Override
    public String getName()
    {
        return hasCustomName() ? customName : "container.mana_grinder";
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
        compound.setInteger("grindProgress", grindProgress);
        compound.setInteger("storedMana", storedMana);
        ItemStackHelper.saveAllItems(compound, inventory);
        if(hasCustomName()) compound.setString("customName", customName);
        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        grindProgress = compound.getInteger("grindProgress");
        totalGrindTime = getItemGrindTime(inventory.get(0));
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
        return grindProgress > 0;
    }
    private boolean canGrind()
    {
        ItemStack inputStack = inventory.get(0);
        if (inputStack.isEmpty()) return false;
        else
        {
            ItemStack grinderResult = ManaGrinderRecipes.instance().getGrinderResult(inputStack);
            if (grinderResult.isEmpty()) return false;

            ItemStack output1 = inventory.get(1);
            ItemStack output2 = inventory.get(2);
            ItemStack output3 = inventory.get(3);
            if (output1.isEmpty() || output2.isEmpty() || output3.isEmpty()) return true;
            if (!output1.isItemEqual(grinderResult) && !output2.isItemEqual(grinderResult) && !output3.isItemEqual(grinderResult)) return false;

            int freeItems = 0;
            if(output1.isItemEqual(grinderResult)) freeItems += getInventoryStackLimit() - output1.getCount();
            if(output3.isItemEqual(grinderResult)) freeItems += getInventoryStackLimit() - output2.getCount();
            if(output1.isItemEqual(grinderResult)) freeItems += getInventoryStackLimit() - output3.getCount();

            return freeItems - grinderResult.getCount() >= 0;
        }
    }
    public static int getItemGrindTime(ItemStack grindStack)
    {
        if(grindStack.isEmpty()) return 0;

        Item item = grindStack.getItem();
        if(item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR)
        {
            Block block = Block.getBlockFromItem(item);
            if(block == ModBlocks.DEEP_CRYSTAL_ORE) return 400;
            if(block == ModBlocks.DEEP_CRYSTAL_END_ORE) return 400;
            if(block == ModBlocks.DEEP_CRYSTAL_NETHER_ORE) return 400;
        } else if(item == ModItems.DEEP_CRYSTAL) return 200;

        return 100;
    }
    public static boolean isItemGrindable(ItemStack item)
    {
        return getItemGrindTime(item) > 0;
    }
    private void grindItem()
    {
        ItemStack input = inventory.get(0);
        ItemStack result = ManaGrinderRecipes.instance().getGrinderResult(input);
        if(!result.isEmpty())
        {
            ItemStack output1 = inventory.get(1);
            ItemStack output2 = inventory.get(2);
            ItemStack output3 = inventory.get(3);

            if(output1.getItem() == result.getItem()) output1.grow(result.getCount());
            else if(output2.getItem() == result.getItem()) output2.grow(result.getCount());
            else if(output3.getItem() == result.getItem()) output3.grow(result.getCount());
            else if(output1.isEmpty()) inventory.set(1, result);
            else if(output2.isEmpty()) inventory.set(2, result);
            else if(output3.isEmpty()) inventory.set(3, result);

            input.shrink(1);
        }
    }
}
