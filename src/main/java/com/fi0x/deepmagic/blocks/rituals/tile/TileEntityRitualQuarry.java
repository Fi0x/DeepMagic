package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.blocks.rituals.RITUAL_TYPE;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

public class TileEntityRitualQuarry extends TileEntityRitualStone
{
    private int digX;
    private int digY;
    private int digZ;
    private int currentDigRadius;
    private int maxDigRadius;

    private STATUS currentState;
    //TODO: Store packed structure blocks

    public TileEntityRitualQuarry()
    {
        type = RITUAL_TYPE.QUARRY;
        manaCosts = ConfigHandler.ritualQuarryManaCosts;
        setReady();
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        compound.setInteger("lastDigX", digX);
        compound.setInteger("lastDigY", digY);
        compound.setInteger("lastDigZ", digZ);
        compound.setInteger("currentRadius", currentDigRadius);
        compound.setInteger("maxDigRadius", maxDigRadius);
        compound.setInteger("currentStatus", currentState.ordinal());

        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        digX = compound.getInteger("lastDigX");
        digY = compound.getInteger("lastDigY");
        digZ = compound.getInteger("lastDigZ");
        currentDigRadius = compound.getInteger("currentRadius");
        maxDigRadius = compound.getInteger("maxDigRadius");
        currentState = STATUS.values()[compound.getInteger("currentStatus")];

        super.readFromNBT(compound);
    }

    @Override
    protected void syncedUpdate()
    {
        switch(currentState)
        {
            case DIG:
                if(setNextBlock())
                {
                    IBlockState state = world.getBlockState(new BlockPos(digX, digY, digZ));
                    ItemStack stack = new ItemStack(state.getBlock().getItemDropped(state, world.rand, 0), state.getBlock().quantityDropped(state, 0, world.rand));
                    world.setBlockToAir(new BlockPos(digX, digY, digZ));
                    if(stack.isEmpty()) return;

                    TileEntity storage = world.getTileEntity(pos.up());
                    if(storage != null)
                    {
                        if(storage.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
                        {
                            IItemHandler h = storage.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                            if(ItemHandlerHelper.insertItemStacked(h, stack, false).isEmpty()) return;
                        }
                    }
                    EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, stack);
                    item.motionY = Math.random() * 10;
                    world.spawnEntity(item);
                } else currentState = STATUS.PACK;
                break;
            case PACK:
                if(!packNextBlock()) currentState = STATUS.MOVE;
                break;
            case MOVE:
                if(!move()) currentState = STATUS.UNPACK;
                break;
            case UNPACK:
                if(!unpackNextBlock()) setReady();
                break;
        }
    }

    private void setReady()
    {
        digX = pos.getX();
        digY = pos.getY() - 2;
        digZ = pos.getZ();
        currentDigRadius = 0;
        maxDigRadius = 4;
        currentState = STATUS.DIG;
    }
    private boolean setNextBlock()
    {
        while(digY <= 0 || world.isAirBlock(new BlockPos(digX, digY, digZ)) || world.getBlockState(new BlockPos(digX, digY, digZ)).getBlock() == Blocks.BEDROCK)
        {
            digY--;
            if(digY > 0) continue;

            digY = pos.getY() - 2 - currentDigRadius;

            if(digX != pos.getX() + currentDigRadius) digX++;
            else if(digZ != pos.getZ() + currentDigRadius)
            {
                digZ++;
                digX = pos.getX() - currentDigRadius;
            } else if(currentDigRadius < maxDigRadius)
            {
                currentDigRadius++;
                digX = pos.getX() - currentDigRadius;
                digZ = pos.getZ() - currentDigRadius;
                digY--;
            } else return false;

            if(digX != pos.getX() - currentDigRadius && digX != pos.getX() + currentDigRadius && digZ != pos.getZ() - currentDigRadius && digZ != pos.getZ() + currentDigRadius)
            {
                while(digX < pos.getX() + currentDigRadius)
                {
                    digX++;
                }
            }
        }
        return true;
    }
    private boolean packNextBlock()
    {
        //TODO: Remove 1 structure block and store it (Item Capabilities); Return true if successful, false if structure is packed completely
        return false;
    }
    private boolean move()
    {
        //TODO: Move quarry in correct direction; If no block of the structure would have bedrock in sight, return false, else true
        return false;
    }
    private boolean unpackNextBlock()
    {
        //TODO: Place 1 structure block from stored blocks; Return true if successful, false if no more blocks are stored
        return false;
    }

    enum STATUS
    {
        DIG,
        PACK,
        MOVE,
        UNPACK
    }
}