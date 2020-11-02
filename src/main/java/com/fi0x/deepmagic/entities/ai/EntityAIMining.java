package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.entities.mobs.EntityDwarf;
import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.Random;

public class EntityAIMining extends EntityAIBase
{
    protected final int executionChance;
    protected final World world;
    protected final EntityCreature entity;
    protected final double speed;
    protected final int searchRange = 30;
    protected final float probability;
    protected final int maxExecutionHeight = 50;
    protected final Random random;
    protected BlockPos startPosition;
    protected BlockPos destination;
    protected ArrayList<BlockPos> miningBlocks;
    protected BlockPos chestPos;
    private int digDelay;
    private final ArrayList<IBlockState> mineableBlocks;

    public EntityAIMining(EntityDwarf entity)
    {
        this(entity, 1);
    }
    public EntityAIMining(EntityDwarf entity, int speed)
    {
        this(entity, speed, 120);
    }
    public EntityAIMining(EntityDwarf entity, int speed, int executionChance)
    {
        this.setMutexBits(3);
        this.entity = entity;
        this.world = entity.world;
        this.probability = 0.001F;
        this.speed = speed;
        this.executionChance = executionChance;
        random = new Random();
        miningBlocks = new ArrayList<>();

        mineableBlocks = new ArrayList<>();
        mineableBlocks.add(Blocks.AIR.getDefaultState());
        mineableBlocks.add(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
        mineableBlocks.add(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
        mineableBlocks.add(Blocks.GRASS.getDefaultState());
        mineableBlocks.add(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL));
        mineableBlocks.add(Blocks.MYCELIUM.getDefaultState());
        mineableBlocks.add(Blocks.GRAVEL.getDefaultState());
        mineableBlocks.add(Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.SAND));
        mineableBlocks.add(Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND));
        mineableBlocks.add(Blocks.CLAY.getDefaultState());
        mineableBlocks.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE));
        mineableBlocks.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE));
        mineableBlocks.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE));
        mineableBlocks.add(Blocks.OBSIDIAN.getDefaultState());
        mineableBlocks.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE));
        mineableBlocks.add(Blocks.COAL_ORE.getDefaultState());
        mineableBlocks.add(Blocks.IRON_ORE.getDefaultState());
        mineableBlocks.add(Blocks.GOLD_ORE.getDefaultState());
        mineableBlocks.add(Blocks.DIAMOND_ORE.getDefaultState());
        mineableBlocks.add(Blocks.EMERALD_ORE.getDefaultState());
        mineableBlocks.add(Blocks.REDSTONE_ORE.getDefaultState());
        mineableBlocks.add(Blocks.LAPIS_ORE.getDefaultState());
        mineableBlocks.add(Blocks.QUARTZ_ORE.getDefaultState());
        mineableBlocks.add(Blocks.NETHERRACK.getDefaultState());
        mineableBlocks.add(Blocks.SOUL_SAND.getDefaultState());
        mineableBlocks.add(Blocks.END_STONE.getDefaultState());
        mineableBlocks.add(ModBlocks.DEEP_CRYSTAL_ORE.getDefaultState());
        mineableBlocks.add(ModBlocks.DEEP_CRYSTAL_NETHER_ORE.getDefaultState());
        mineableBlocks.add(ModBlocks.DEEP_CRYSTAL_END_ORE.getDefaultState());
        mineableBlocks.add(ModBlocks.INSANITY_STONE.getDefaultState());
        mineableBlocks.add(ModBlocks.INSANITY_DIRT.getDefaultState());
        mineableBlocks.add(ModBlocks.INSANITY_GRASS.getDefaultState());
    }

    @Override
    public boolean shouldExecute()
    {
        if (this.entity.getIdleTime() >= 100 || this.entity.getRNG().nextInt(this.executionChance) != 0) return false;
        return entity.posY <= maxExecutionHeight;
    }

    @Override
    public void startExecuting()
    {
        chestPos = findChest(entity.getPosition());

        startPosition = entity.getPosition();
        destination = getRandomPosition();
        digDelay = 0;

        getMiningBlocks(startPosition, destination);
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        if(entity.getNavigator().noPath())
        {
            if(digDelay == 0 && !miningBlocks.isEmpty())
            {
                digAtBlockPos(miningBlocks.get(0));
                entity.getNavigator().tryMoveToXYZ(miningBlocks.get(0).getX() + 0.5, miningBlocks.get(0).getY(), miningBlocks.get(0).getZ() + 0.5, 1);
                miningBlocks.remove(0);
                digDelay = 20;
            } else digDelay--;
            return !miningBlocks.isEmpty();
        } else return true;
    }

    protected void digAtBlockPos(BlockPos pos)
    {
        Block block = world.getBlockState(pos).getBlock();
        TileEntity te = null;
        if(chestPos != null && world.getBlockState(chestPos).getBlock() != Blocks.CHEST) chestPos = findChest(entity.getPosition());
        try
        {
            assert chestPos != null;
            te = world.getTileEntity(chestPos);
        } catch (Exception ignored)
        {
        }

        if(te == null)
        {
            world.getBlockState(pos).getBlock().dropBlockAsItem(world, pos, world.getBlockState(pos).getBlock().getDefaultState(), 1);
            chestPos = findChest(entity.getPosition());
        }
        else
        {
            ItemStack dropppedItemStack;
            if(block.getDefaultState() == Blocks.LAPIS_ORE.getDefaultState()) dropppedItemStack = new ItemStack(Items.DYE, block.quantityDropped(random), 4);
            else dropppedItemStack = new ItemStack(block.getItemDropped(world.getBlockState(pos), random, 1), block.quantityDropped(random));

            IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            ItemHandlerHelper.insertItemStacked(handler, dropppedItemStack, false);
        }
        world.setBlockToAir(pos);
    }
    protected void getMiningBlocks(BlockPos start, BlockPos end)
    {
        int xDifference = 0;
        int zDifference = 0;

        if(start.getX() == end.getX())
        {
            if(start.getZ() < end.getZ()) zDifference = 1;
            else zDifference = -1;
        } else
        {
            if(start.getX() < end.getX()) xDifference = 1;
            else xDifference = -1;
        }

        while(start != end && miningBlocks.size() < 30 && mineableBlocks.contains(world.getBlockState(start)) && mineableBlocks.contains(world.getBlockState(start.add(0, 1, 0))))
        {
            miningBlocks.add(start);
            miningBlocks.add(start.add(0, 1, 0));
            start = start.add(xDifference, 0, zDifference);
        }
    }
    protected BlockPos getRandomPosition()
    {
        int xIncrease = 0;
        int zIncrease = 0;
        if(Math.random() < 0.5) zIncrease = random.nextInt(searchRange / 2 - searchRange / 4);
        else xIncrease = random.nextInt(searchRange / 2 - searchRange / 4);

        return entity.getPosition().add(xIncrease, 0, zIncrease);
    }
    protected BlockPos findChest(BlockPos pos)
    {
        int height = searchRange / 4;

        for(int range = 0; range <= searchRange; range++)
        {
            int x = -range;
            int z = -range;
            for(int y = -height; y <= height; y++)
            {
                for(; x <= range; x++)
                {
                    if(world.getBlockState(pos.add(x, y, z)).getBlock() instanceof BlockChest) return pos.add(x, y, z);
                }
                for(; z <= range; z++)
                {
                    if(world.getBlockState(pos.add(x, y, z)).getBlock() instanceof BlockChest) return pos.add(x, y, z);
                }
                for(; x >= -range; x--)
                {
                    if(world.getBlockState(pos.add(x, y, z)).getBlock() instanceof BlockChest) return pos.add(x, y, z);
                }
                for(; z >= -range; z--)
                {
                    if(world.getBlockState(pos.add(x, y, z)).getBlock() instanceof BlockChest) return pos.add(x, y, z);
                }
            }
        }
        return null;
    }
}