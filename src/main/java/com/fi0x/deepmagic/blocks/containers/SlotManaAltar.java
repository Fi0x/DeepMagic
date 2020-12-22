package com.fi0x.deepmagic.blocks.containers;

import com.fi0x.deepmagic.items.spells.ManaChargedSpell;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class SlotManaAltar extends Slot
{
    public SlotManaAltar(IInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
    }
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        return stack.getItem() instanceof ManaChargedSpell;
    }
}
