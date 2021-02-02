package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.blocks.rituals.RITUAL_TYPE;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public class TileEntityRitualQuarry extends TileEntityRitualStone
{
    private int digX;
    private int digY;
    private int digZ;
    private int currentDigRadius;
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
                    //TODO: Dig block at x, y, z
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
        /*
        TODO: Use a redstone structure block to have the quarry active
         */
    }

    private void setReady()
    {
        digX = pos.getX();
        digY = pos.getY() - 2;
        digZ = pos.getZ();
        currentDigRadius = 0;
        currentState = STATUS.DIG;
    }
    private boolean setNextBlock()
    {
        //TODO: Change x, y, z; Return true if block is not mined, else return false to signal packing
        return false;
    }
    private boolean packNextBlock()
    {
        //TODO: Remove 1 structure block and store it; Return true if successful, false if structure is packed completely
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