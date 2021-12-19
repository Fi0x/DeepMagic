package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.blocks.rituals.RITUAL_TYPE;
import com.fi0x.deepmagic.blocks.rituals.structureblocks.RitualStructure;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.network.PacketGetRitual;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.util.handlers.PacketHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.Random;

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
        direction = EnumFacing.Plane.HORIZONTAL.random(new Random());
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
    public String getPacketData()
    {
        StringBuilder packetString = new StringBuilder();

        packetString.append(digX).append("-");
        packetString.append(digY).append("-");
        packetString.append(digZ).append("-");
        packetString.append(currentDigRadius).append("-");
        packetString.append(maxDigRadius).append("-");
        packetString.append(direction.ordinal()).append("-");
        packetString.append(currentState.ordinal());

        for (int structureBlock : structureBlocks)
        {
            packetString.append("-");
            packetString.append(structureBlock);
        }

        return packetString.toString();
    }
    @Override
    public void setDataFromPacket(String data)
    {
        String[] packetData = data.split("-");

        digX = Integer.parseInt(packetData[0]);
        digY = Integer.parseInt(packetData[1]);
        digZ = Integer.parseInt(packetData[2]);
        currentDigRadius = Integer.parseInt(packetData[3]);
        maxDigRadius = Integer.parseInt(packetData[4]);
        direction = EnumFacing.values()[Integer.parseInt(packetData[5])];
        currentState = STATUS.values()[Integer.parseInt(packetData[6])];

        for (int i = 0; i < structureBlocks.length; i++)
        {
            structureBlocks[i] = Integer.parseInt(packetData[i + 7]);
        }
    }

    @Override
    public void syncedUpdate()
    {
        if(!world.isRemote)
        {
            switch(currentState)
            {
                case DIG:
                    if(setNextBlock()) digNextBlock();
                    else
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
        else PacketHandler.INSTANCE.sendToServer(new PacketGetRitual(world.provider.getDimension(), pos));
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
        while(digY <= 0
                || world.isAirBlock(new BlockPos(digX, digY, digZ))
                || world.getBlockState(new BlockPos(digX, digY, digZ)).getBlock() == Blocks.BEDROCK
                || world.containsAnyLiquid(new AxisAlignedBB(digX, digY, digZ, digX + 1, digY + 1, digZ + 1)))
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
    private boolean digNextBlock()
    {
        System.out.println("Digging block at " + digX + ", " + digY + ", " + digZ);
        IBlockState state = world.getBlockState(new BlockPos(digX, digY, digZ));
        ItemStack stack = new ItemStack(state.getBlock().getItemDropped(state, world.rand, 0), state.getBlock().quantityDropped(state, 0, world.rand));
        world.setBlockToAir(new BlockPos(digX, digY, digZ));
        if(stack.isEmpty()) return true;

        TileEntity storage = world.getTileEntity(pos.up());
        if(storage != null)
        {
            if(storage.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
            {
                IItemHandler h = storage.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if(ItemHandlerHelper.insertItemStacked(h, stack, false).isEmpty()) return true;
                System.out.println("Quarry ritual could not store stack in inventory");
            }
        }
        EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, stack);
        item.motionY = Math.random() * 0.25 + 0.25;
        world.spawnEntity(item);

        return false;
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
                    world.setBlockState(position, ModBlocks.RITUAL_STRUCTURE.getDefaultState());
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