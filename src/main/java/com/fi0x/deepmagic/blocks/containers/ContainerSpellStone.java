package com.fi0x.deepmagic.blocks.containers;

import com.fi0x.deepmagic.blocks.mana.tile.TileEntitySpellStone;
import com.fi0x.deepmagic.items.spells.Spell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ContainerSpellStone extends Container
{
    private final TileEntitySpellStone te;
    private int totalTime, remainingTime, pressedButton;

    public ContainerSpellStone(InventoryPlayer player, TileEntitySpellStone tileEntity)
    {
        te = tileEntity;

        addSlotToContainer(new SlotSpellStoneSpellInput(te, 0, 148, 20));
        addSlotToContainer(new SlotOutput(te, 1, 148, 84));
        for(int idx = 2; idx <= 6; idx++)
        {
            addSlotToContainer(new Slot(te, idx, 12 + 18 * (idx - 2), 20));
        }

        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 140 + y * 18));
            }
        }
        for(int x = 0; x < 9; x++)
        {
            addSlotToContainer(new Slot(player, x, 8 + x * 18, 198));
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

        for(IContainerListener listener : listeners)
        {
            if(totalTime != te.getField(0)) listener.sendWindowProperty(this, 0, te.getField(0));
            if(remainingTime != te.getField(1)) listener.sendWindowProperty(this, 1, te.getField(1));
            if(pressedButton != te.getField(2)) listener.sendWindowProperty(this, 2, te.getField(2));
        }

        totalTime = te.getField(0);
        remainingTime = te.getField(1);
        pressedButton = te.getField(2);
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

        if(slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if(index <= 6)
            {
                if(!this.mergeItemStack(itemstack1, 7, 43, true)) return ItemStack.EMPTY;
                slot.onSlotChange(itemstack1, itemstack);
            } else
            {
                if(itemstack1.getItem() instanceof Spell)
                {
                    if(!this.mergeItemStack(itemstack1, 0, 0, false)) return ItemStack.EMPTY;
                } else if(index < 34)
                {
                    if(!this.mergeItemStack(itemstack1, 34, 43, false)) return ItemStack.EMPTY;
                } else if(index < 43 && !this.mergeItemStack(itemstack1, 7, 34, false)) return ItemStack.EMPTY;
            }
            if(itemstack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();

            if(itemstack1.getCount() == itemstack.getCount()) return ItemStack.EMPTY;

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}