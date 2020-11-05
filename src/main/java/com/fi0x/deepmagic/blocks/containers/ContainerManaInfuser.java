package com.fi0x.deepmagic.blocks.containers;

import com.fi0x.deepmagic.blocks.tileentity.TileEntityManaInfuser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ContainerManaInfuser extends Container
{
    private final TileEntityManaInfuser te;
    private int infusionProgress, totalInfusionTime, storedMana;

    public ContainerManaInfuser(InventoryPlayer player, TileEntityManaInfuser tileEntity)
    {
        te = tileEntity;

        addSlotToContainer(new SlotManaInfuser(te, 0, 21, 25));
        addSlotToContainer(new SlotManaInfuserOutput(te, 1, 86, 25));

        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }
        for(int x = 0; x < 9; x++)
        {
            addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
        }
    }

    @Override
    public void addListener(@Nonnull IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, te);
    }
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (IContainerListener listener : listeners)
        {
            if (infusionProgress != te.getField(0)) listener.sendWindowProperty(this, 0, te.getField(0));
            if (totalInfusionTime != te.getField(1)) listener.sendWindowProperty(this, 1, te.getField(1));
            if (storedMana != te.getField(2)) listener.sendWindowProperty(this, 2, te.getField(2));
        }

        infusionProgress = te.getField(0);
        totalInfusionTime = te.getField(1);
        storedMana = te.getField(2);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        te.setField(id, data);
    }
    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn)
    {
        return te.isUsableByPlayer(playerIn);
    }
    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index) //TODO: Change values to fit
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 1)
            {
                if (!this.mergeItemStack(itemstack1, 2, 38, true)) return ItemStack.EMPTY;
                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 0)
            {
                if (TileEntityManaInfuser.isItemInfusable(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 0, 0, false)) return ItemStack.EMPTY;
                } else if (index < 29)
                {
                    if (!this.mergeItemStack(itemstack1, 29, 38, false)) return ItemStack.EMPTY;
                } else if (index < 38 && !this.mergeItemStack(itemstack1, 2, 29, false)) return ItemStack.EMPTY;
            } else if (!this.mergeItemStack(itemstack1, 2, 38, false)) return ItemStack.EMPTY;

            if (itemstack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();

            if (itemstack1.getCount() == itemstack.getCount()) return ItemStack.EMPTY;

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}