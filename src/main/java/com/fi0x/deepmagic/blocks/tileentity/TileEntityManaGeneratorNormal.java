package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.blocks.mana.ManaGeneratorNormal;
import com.fi0x.deepmagic.util.IManaTileEntity;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;

public class TileEntityManaGeneratorNormal extends TileEntity implements IInventory, ITickable, IManaTileEntity
{
    private BlockPos manaTargetPos;
    private NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    private String customName;

    private TileEntity linkedTE;
    private int burnTime;
    private int currentBurnTime;
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
        return isItemFuel(stack) || index != 0;
    }
    @Override
    public int getField(int id)
    {
        switch(id)
        {
            case 0:
                return burnTime;
            case 1:
                return currentBurnTime;
            case 2:
                return storedMana;
        }
        return 0;
    }
    @Override
    public void setField(int id, int value)
    {
        switch(id)
        {
            case 0:
                burnTime = value;
                break;
            case 1:
                currentBurnTime = value;
                break;
            case 2:
                storedMana = value;
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
        if(isRunning())
        {
            burnTime--;
            if(storedMana < ConfigHandler.manaGeneratorManaCapacity) storedMana++;
            dirty = true;
        }
        if(world.isRemote) return;

        ItemStack stack = inventory.get(0);

        if(!isRunning() && storedMana < ConfigHandler.manaGeneratorManaCapacity)
        {
            if(!stack.isEmpty())
            {
                burnTime = getItemBurnTime(stack);
                currentBurnTime = burnTime;
                stack.shrink(1);
                inventory.set(0, stack);
                dirty = true;
            }
        }
        if(isRunning() != wasRunning) ManaGeneratorNormal.setState(isRunning(), world, pos);
        if(storedMana >= 20)
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
        return hasCustomName() ? customName : "container.mana_generator_normal";
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

        compound.setInteger("burnTime", burnTime);
        compound.setInteger("storedMana", storedMana);
        ItemStackHelper.saveAllItems(compound, inventory);
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

        burnTime = compound.getInteger("burnTime");
        currentBurnTime = getItemBurnTime(inventory.get(0));
        storedMana = compound.getInteger("storedMana");
        inventory = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inventory);
        if(compound.hasKey("customName")) setCustomName(compound.getString("customName"));
        super.readFromNBT(compound);
    }
    @Override
    public double getSpaceForMana()
    {
        return ConfigHandler.manaGeneratorManaCapacity - storedMana;
    }
    @Override
    public double addManaToStorage(double amount)
    {
        double ret = amount - (ConfigHandler.manaGeneratorManaCapacity - storedMana);
        if(ret > 0) storedMana = ConfigHandler.manaGeneratorManaCapacity;
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
        return burnTime > 0;
    }
    public static int getItemBurnTime(ItemStack fuel)
    {
        if(fuel.isEmpty()) return 0;

        Item item = fuel.getItem();
        if(item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR)
        {
            Block block = Block.getBlockFromItem(item);
            if(block == Blocks.WOODEN_SLAB) return 150;
            if(block.getDefaultState().getMaterial() == Material.WOOD) return 300;
            if(block == Blocks.COAL_BLOCK) return 16000;
        }
        if(item == Items.STICK) return 100;
        if(item == Items.COAL) return 1600;
        if(item == Items.LAVA_BUCKET) return 20000;
        if(item == Item.getItemFromBlock(Blocks.SAPLING)) return 100;
        if(item == Items.BLAZE_ROD) return 2400;
        return ForgeEventFactory.getItemBurnTime(fuel);
    }
    public static boolean isItemFuel(ItemStack fuel)
    {
        return getItemBurnTime(fuel) > 0;
    }
    public void setManaTargetPos(BlockPos blockPos)
    {
        if(this.getDistanceSq(blockPos.getX(), blockPos.getY(), blockPos.getZ()) > ConfigHandler.manaBlockTransferRange * ConfigHandler.manaBlockTransferRange) manaTargetPos = blockPos;
        else manaTargetPos = null;
        if(manaTargetPos == null) linkedTE = null;
        else linkedTE = world.getTileEntity(manaTargetPos);
    }
}
