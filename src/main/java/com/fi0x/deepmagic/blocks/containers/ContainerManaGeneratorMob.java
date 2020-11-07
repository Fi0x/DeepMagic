package com.fi0x.deepmagic.blocks.containers;

import com.fi0x.deepmagic.blocks.tileentity.TileEntityManaGeneratorMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ContainerManaGeneratorMob extends Container
{
    private final TileEntityManaGeneratorMob te;
    private int cooldown, storedMana;

    public ContainerManaGeneratorMob(InventoryPlayer player, TileEntityManaGeneratorMob tileEntity)
    {
        te = tileEntity;

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
            if (cooldown != te.getField(0)) listener.sendWindowProperty(this, 0, te.getField(0));
            if (storedMana != te.getField(1)) listener.sendWindowProperty(this, 1, te.getField(1));
        }

        cooldown = te.getField(0);
        storedMana = te.getField(1);
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
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 27)
            {
                if (!this.mergeItemStack(itemstack1, 27, 36, false)) return ItemStack.EMPTY;
            } else if (index < 36 && !this.mergeItemStack(itemstack1, 0, 27, false)) return ItemStack.EMPTY;

            if (itemstack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();

            if (itemstack1.getCount() == itemstack.getCount()) return ItemStack.EMPTY;
            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}