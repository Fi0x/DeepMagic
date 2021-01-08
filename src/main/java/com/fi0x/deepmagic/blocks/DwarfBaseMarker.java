package com.fi0x.deepmagic.blocks;

import com.fi0x.deepmagic.entities.mobs.EntityDwarf;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class DwarfBaseMarker extends BlockBase
{
    public DwarfBaseMarker(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(5.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 2);
        setLightLevel(0.5F);
        setTickRandomly(true);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand)
    {
        if(rand.nextInt(15) + 1 > ConfigHandler.dwarfBaseMarkerParticles) return;

        if(worldIn.getBlockState(pos.up()).getCollisionBoundingBox(worldIn, pos.up()) == null)
        {
            double d0 = (double) pos.getX() + Math.random();
            double d1 = (double) pos.getY() + 1;
            double d2 = (double) pos.getZ() + Math.random();

            worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void randomTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random random)
    {
        if(random.nextInt(15) + 1 > ConfigHandler.dwarfMarkerSpawnChance) return;

        if(random.nextInt(128) == 0)
        {
            int tries = 0;
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            while((x == pos.getX() && z == pos.getZ()) || worldIn.getBlockState(new BlockPos(x, y, z)).getCollisionBoundingBox(worldIn, new BlockPos(x, y, z)) != null)
            {
                x += (int) ((Math.random() * 5) - 2);
                z += (int) ((Math.random() * 5) - 2);

                tries++;
                if(tries > 10) return;
            }

            EntityDwarf dwarf = new EntityDwarf(worldIn);
            dwarf.setLocationAndAngles(x, y, z, 0, 0);
            worldIn.spawnEntity(dwarf);
        }
    }
}