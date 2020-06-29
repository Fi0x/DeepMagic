package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.entities.EntityDwarf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class EntityAIMining extends EntityAIBase
{
    protected final EntityDwarf creature;
    private BlockPos destination;
    private World world;
    private List<IBlockState> ores;

    public EntityAIMining(EntityDwarf creature, World world)
    {
        this.creature = creature;
        this.setMutexBits(1);
        this.world = world;
        fillOreList();
    }

    public boolean shouldExecute()
    {
        return !creature.isMining && creature.posY < 50;
    }

    public void startExecuting()
    {
        this.creature.isMining = true;
        destination = findOre(creature.getPosition());
        //TODO: create route-finding
    }

    public boolean shouldContinueExecuting()
    {
        if(creature.getPosition() == destination)
        {
            creature.isMining = false;
            return false;
        }
        return true;
    }

    private BlockPos findOre(BlockPos pos)
    {
        for(int range = 1; range < 5; range++)
        {
            for(int x = pos.getX() - range; x < pos.getX() + range; x++)
            {
                for(int z = pos.getZ() - range; z < pos.getZ() + range; z++)
                {
                    BlockPos currentBlock = new BlockPos(x, pos.getY(), z);
                    if(ores.contains(world.getBlockState(currentBlock).getBlock().getDefaultState()))
                    {
                        return currentBlock;
                    }
                }
            }
        }
        return new BlockPos(creature.posX, creature.posY, creature.posZ + 10);
    }

    private void fillOreList()
    {
        ores.add(Blocks.COAL_ORE.getDefaultState());
        ores.add(Blocks.DIAMOND_ORE.getDefaultState());
        ores.add(Blocks.EMERALD_ORE.getDefaultState());
        ores.add(Blocks.GOLD_ORE.getDefaultState());
        ores.add(Blocks.IRON_ORE.getDefaultState());
        ores.add(Blocks.LAPIS_ORE.getDefaultState());
        ores.add(Blocks.REDSTONE_ORE.getDefaultState());
        ores.add(Blocks.QUARTZ_ORE.getDefaultState());
    }
}