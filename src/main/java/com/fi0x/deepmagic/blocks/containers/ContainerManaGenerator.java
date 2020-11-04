package com.fi0x.deepmagic.blocks.containers;

import com.fi0x.deepmagic.blocks.tileentity.TileEntityManaGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ContainerManaGenerator extends Container
{
    private final TileEntityManaGenerator te;
    private int burnTime, currentBurnTime;

    public ContainerManaGenerator(InventoryPlayer player, TileEntityManaGenerator tileEntity)
    {
        te = tileEntity;

        addSlotToContainer(new SlotManaGenerator(te, 0, 82, 30));

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
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, te);
    }
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for(int i = 0; i < listeners.size(); i++)
        {
            IContainerListener listener = listeners.get(i);

            if(burnTime != te.getField(0)) listener.sendWindowProperty(this, 0, te.getField(0));
            if(currentBurnTime != te.getField(1)) listener.sendWindowProperty(this, 1, te.getField(1));
        }

        burnTime = te.getField(0);
        currentBurnTime = te.getField(1);
    }
    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn)
    {
        return te.isUsableByPlayer(playerIn);
    }
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        //TODO: implement this method (Harry Talks - Furnace pt4)
        return super.transferStackInSlot(playerIn, index);
    }
}