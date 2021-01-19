package com.fi0x.deepmagic.blocks;

import com.fi0x.deepmagic.entities.mobs.EntityDwarf;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class DwarfSpawner extends BlockBase
{
    public DwarfSpawner(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(10.0F);
        setResistance(10.0F);
        setHarvestLevel("pickaxe", 2);
        setLightLevel(0.5F);
        setTickRandomly(true);
    }

    @Override
    public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand)
    {
        for(int i = 0; i < ConfigHandler.dwarfSpawnerSpawns; i++)
        {
            int tries = 0;
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();

            while((x == pos.getX() && z == pos.getZ()) || world.getBlockState(new BlockPos(x, y, z)).getCollisionBoundingBox(world, new BlockPos(x, y, z)) != null)
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
            }
        }

        world.setBlockState(pos, ModBlocks.DWARF_BASE_MARKER.getDefaultState());
    }
}