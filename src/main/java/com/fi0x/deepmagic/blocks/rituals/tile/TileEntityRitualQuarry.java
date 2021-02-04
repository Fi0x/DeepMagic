package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.blocks.rituals.RITUAL_TYPE;
import com.fi0x.deepmagic.blocks.rituals.structureblocks.RitualStructure;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
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
    private EnumFacing direction;

    private STATUS currentState;
    private int[] structureBlocks;

    public TileEntityRitualQuarry()
    {
        type = RITUAL_TYPE.QUARRY;
        manaCosts = ConfigHandler.ritualQuarryManaCosts;
        direction = EnumFacing.Plane.HORIZONTAL.random(world.rand);//TODO: Create a way for the player to set directions
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
        compound.setInteger("direction", direction.ordinal());

        compound.setInteger("currentStatus", currentState.ordinal());
        compound.setIntArray("structureBlocks", structureBlocks);

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
        direction = EnumFacing.values()[compound.getInteger("direction")];

        currentState = STATUS.values()[compound.getInteger("currentStatus")];
        structureBlocks = compound.getIntArray("structureBlocks");

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
                } else
                {
                    currentState = STATUS.PACK;
                    markDirty();
                }
                break;
            case PACK:
                if(!packNextBlock()) currentState = STATUS.MOVE;
                markDirty();
                break;
            case MOVE:
                if(!move()) currentState = STATUS.UNPACK;
                markDirty();
                break;
            case UNPACK:
                if(!unpackNextBlock()) setReady();
                markDirty();
                break;
        }
    }

    public EnumFacing nextDirection()
    {
        int idx = (direction.ordinal() - 1) % 4;
        direction = EnumFacing.Plane.HORIZONTAL.facings()[idx];
        return direction;
    }

    private void setReady()
    {
        digX = pos.getX();
        digY = pos.getY() - 2;
        digZ = pos.getZ();
        currentDigRadius = 0;
        maxDigRadius = 4;
        currentState = STATUS.DIG;
        structureBlocks = new int[1];
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
        BlockPos position = QuarryHelper.getRandomFilledStructurePos(world, pos);
        if(position == null) return false;

        if(world.getBlockState(position).getBlock() instanceof RitualStructure)
        {
            structureBlocks[((RitualStructure) world.getBlockState(position).getBlock()).getStructureType()]++;
            world.setBlockToAir(position);
            return true;
        }
        return false;
    }
    private boolean move()
    {
        TileEntity te = world.getTileEntity(pos);
        world.setBlockState(pos.offset(direction), ModBlocks.RITUAL_QUARRY.getDefaultState());
        world.setTileEntity(pos.offset(direction), te);

        for(BlockPos checkPos = pos.down().offset(direction.getOpposite(), maxDigRadius); checkPos.getY() > 0; checkPos.down())
        {
            if(world.isAirBlock(checkPos)) continue;
            return world.getBlockState(checkPos).getBlock() == Blocks.BEDROCK;
        }
        return false;
    }
    private boolean unpackNextBlock()
    {
        for(int type = 0; type < structureBlocks.length; type++)
        {
            if(structureBlocks[type] == 0) continue;
            BlockPos position = QuarryHelper.getRandomFreeStructurePos(world, pos);
            if(position == null) return true;
            switch(type)
            {
                case 0:
                    world.setBlockState(position, ModBlocks.RITUAL_BASE.getDefaultState());
                    break;
                default:
                    break;
            }
            structureBlocks[type]--;
            return true;
        }
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