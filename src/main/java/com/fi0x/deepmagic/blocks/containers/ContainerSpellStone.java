package com.fi0x.deepmagic.blocks.containers;

import com.fi0x.deepmagic.blocks.mana.tile.TileEntityManaAltar;
import com.fi0x.deepmagic.blocks.mana.tile.TileEntitySpellStone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ContainerSpellStone extends Container
{
    private final TileEntitySpellStone te;
    private int storedMana;

    public ContainerSpellStone(InventoryPlayer player, TileEntitySpellStone tileEntity)
    {
        te = tileEntity;

        addSlotToContainer(new SlotSpellStoneSpellInput(te, 0, 80, 30));//TODO: Add missing slots

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
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn)
    {
        return te.isUsableByPlayer(playerIn);
    }
    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index)//TODO: Set correct slots
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if(index == 0)
            {
                if(!this.mergeItemStack(itemstack1, 1, 37, true)) return ItemStack.EMPTY;
                slot.onSlotChange(itemstack1, itemstack);
            } else
            {
                if(TileEntityManaAltar.isItemChargeable(itemstack1))
                {
                    if(!this.mergeItemStack(itemstack1, 0, 0, false)) return ItemStack.EMPTY;
                } else if(index < 28)
                {
                    if(!this.mergeItemStack(itemstack1, 28, 37, false)) return ItemStack.EMPTY;
                } else if(index < 37 && !this.mergeItemStack(itemstack1, 1, 28, false)) return ItemStack.EMPTY;
            }
            if(itemstack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();

            if(itemstack1.getCount() == itemstack.getCount()) return ItemStack.EMPTY;

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}