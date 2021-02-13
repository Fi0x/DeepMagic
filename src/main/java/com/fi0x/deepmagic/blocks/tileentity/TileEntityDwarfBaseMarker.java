package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.entities.mobs.EntityDwarf;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class TileEntityDwarfBaseMarker extends TileEntity implements ITickable
{
    private int delay = 200;

    @Override
    public void update()
    {
        if(!world.isRemote)
        {
            delay--;
            if(delay > 0) return;
            delay = 200;

            if(Math.random() * 1000 + 1 < ConfigHandler.dwarfMarkerSpawnChance) spawnDwarf();
        }
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
