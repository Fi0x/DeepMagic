package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.blocks.rituals.RITUAL_TYPE;
import com.fi0x.deepmagic.blocks.rituals.structureblocks.RitualStructure;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.network.PacketGetRitual;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.util.handlers.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
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
    private int digX = 0;
    private int digY = -2;
    private int digZ = 0;
    private int currentDigRadius = 0;
    private int maxDigRadius = 4;
    private EnumFacing direction;

    private STATUS currentState = STATUS.DIG;
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
    public String getPacketData()//TODO: Find out why so many packets are sent
    {
        StringBuilder packetString = new StringBuilder();

        packetString.append(digX).append("###");
        packetString.append(digY).append("###");
        packetString.append(digZ).append("###");
        packetString.append(currentDigRadius).append("###");
        packetString.append(maxDigRadius).append("###");
        packetString.append(direction.ordinal()).append("###");
        packetString.append(currentState.ordinal());

        for (int structureBlock : structureBlocks)
        {
            packetString.append("###");
            packetString.append(structureBlock);
        }
        return packetString.toString();
    }
    @Override
    public void setDataFromPacket(String data)
    {
        String[] packetData = data.split("###");

        if(!packetData[0].equals(""))
            digX = Integer.parseInt(packetData[0]);
        if(!packetData[1].equals(""))
            digY = Integer.parseInt(packetData[1]);
        if(!packetData[2].equals(""))
            digZ = Integer.parseInt(packetData[2]);
        if(!packetData[3].equals(""))
            currentDigRadius = Integer.parseInt(packetData[3]);
        if(!packetData[4].equals(""))
            maxDigRadius = Integer.parseInt(packetData[4]);
        if(!packetData[5].equals(""))
            try
            {
                direction = EnumFacing.values()[Integer.parseInt(packetData[5])];
            } catch(IndexOutOfBoundsException ignored)
            {
            }
        if(!packetData[6].equals(""))
            try
            {
                currentState = STATUS.values()[Integer.parseInt(packetData[6])];
            } catch(IndexOutOfBoundsException ignored)
            {
            }

        for (int i = 0; i < packetData.length - 7 && i < structureBlocks.length; i++)
        {
            if(!packetData[i + 7].equals(""))
                structureBlocks[i] = Integer.parseInt(packetData[i + 7]);
        }
    }

    @Override
    public void syncedUpdate()
    {
        if(digY == -100)
        {
            digX = 0;
            digY = -2;
            digZ = 0;
        }
        if(!world.isRemote)
        {
            switch(currentState)
            {
                case DIG:
                    if(setNextBlock())
                    {
                        if(!digNextBlock(new BlockPos(pos.getX() + digX, pos.getY() + digY, pos.getZ() + digZ))) sync *= 4;
                    }
                    else
                    {
                        currentState = STATUS.PACK;
                        needsStructure = false;
                        markDirty();
                    }
                    break;
                case PACK:
                    needsStructure = false;
                    if(!packNextBlock()) currentState = STATUS.MOVE;
                    markDirty();
                    break;
                case MOVE:
                    needsStructure = false;
                    if(!move()) currentState = STATUS.UNPACK;
                    markDirty();
                    break;
                case UNPACK:
                    needsStructure = false;
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
        digY = -100;
        currentDigRadius = 0;
        maxDigRadius = 4;
        currentState = STATUS.DIG;
        structureBlocks = new int[1];
        needsStructure = true;
    }
    private boolean setNextBlock()
    {
        while(pos.getY() + digY <= 0
                || world.isAirBlock(new BlockPos(pos.getX() + digX, pos.getY() + digY, pos.getZ() + digZ))
                || QuarryHelper.isBlacklistedBlock(world, new BlockPos(pos.getX() + digX, pos.getY() + digY, pos.getZ() + digZ))
                || world.containsAnyLiquid(new AxisAlignedBB(pos.getX() + digX, pos.getY() + digY, pos.getZ() + digZ, pos.getX() + digX + 1, pos.getY() + digY + 1, pos.getZ() + digZ + 1)))
        {
            digY--;
            if(pos.getY() + digY > 0) continue;

            digY = - 2 - currentDigRadius;

            if(digX != currentDigRadius) digX++;
            else if(digZ != currentDigRadius)
            {
                digZ++;
                digX = -currentDigRadius;
            } else if(currentDigRadius < maxDigRadius)
            {
                currentDigRadius++;
                digX = -currentDigRadius;
                digZ = -currentDigRadius;
                digY--;
            } else return false;

            if(digX != -currentDigRadius && digX != currentDigRadius && digZ != -currentDigRadius && digZ != currentDigRadius)
            {
                while(digX < currentDigRadius)
                {
                    digX++;
                }
            }
        }
        return true;
    }
    private boolean digNextBlock(BlockPos position)
    {
        if(QuarryHelper.isBlacklistedBlock(world, position))
            return true;

        Block block = world.getBlockState(position).getBlock();
        Item droppedItem = block.getItemDropped(world.getBlockState(position), world.rand, 0);
        int quantity = block.quantityDropped(world.rand);
        int itemMeta = block.damageDropped(world.getBlockState(position));
        ItemStack stack = new ItemStack(droppedItem, quantity, itemMeta);

        world.setBlockToAir(new BlockPos(pos.getX() + digX, pos.getY() + digY, pos.getZ() + digZ));
        if(stack.isEmpty()) return true;

        TileEntity storage = world.getTileEntity(pos.up());
        if(storage != null)
        {
            if(storage.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
            {
                IItemHandler h = storage.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if(ItemHandlerHelper.insertItemStacked(h, stack, false).isEmpty()) return true;
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