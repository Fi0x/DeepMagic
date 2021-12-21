package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.entities.mobs.EntityDwarf;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import java.util.List;

public class TileEntityDwarfBaseMarker extends TileEntity implements ITickable
{
    private int delay = 200;
    private final int PLAYER_ACTIVATION_RANGE = 48;
    private boolean initialSpawnDone = false;

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        compound.setInteger("delay", delay);
        compound.setBoolean("initialSpawnDone", initialSpawnDone);

        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound)
    {
        delay = compound.getInteger("delay");
        initialSpawnDone = compound.getBoolean("initialSpawnDone");

        super.readFromNBT(compound);
    }

    @Override
    public void update()
    {
        if(!isActive()) return;
        if(world.isRemote) return;
        if(!world.isBlockLoaded(pos)) return;

        delay--;
        if(delay > 0) return;
        delay = 200;

        AxisAlignedBB aabb = new AxisAlignedBB(pos.add(-16, -5, -16), pos.add(16, 5, 16));
        List<EntityDwarf> existingDwarfs = world.getEntitiesWithinAABB(EntityDwarf.class, aabb);
        if(existingDwarfs.size() > 10) return;
        if(!initialSpawnDone && existingDwarfs.size() < 2) spawnDwarf();

        initialSpawnDone = true;
        markDirty();

        if(Math.random() * 1000 + 1 < ConfigHandler.dwarfMarkerSpawnChance) spawnDwarf();
    }

    private boolean isActive()
    {
        return world.isAnyPlayerWithinRangeAt((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, PLAYER_ACTIVATION_RANGE);
    }

    private void spawnDwarf()
    {
        int tries = 0;
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        while(world.getBlockState(new BlockPos(x, y, z)).getCollisionBoundingBox(world, new BlockPos(x, y, z)) != null)
        {
            x += (int) ((Math.random() * 5) - 2);
            z += (int) ((Math.random() * 5) - 2);

            tries++;
            if(tries > 50) break;
        }

        if(tries <= 50)
        {
            EntityDwarf dwarf = new EntityDwarf(world);
            dwarf.setLocationAndAngles(x + 0.5, y, z + 0.5, 0, 0);
            world.spawnEntity(dwarf);
            world.playSound(null, pos, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 1, (float) (0.9 + Math.random() * 0.1));
        }
    }
}
