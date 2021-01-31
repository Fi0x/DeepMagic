package com.fi0x.deepmagic.blocks;

import com.fi0x.deepmagic.entities.mobs.EntityDemon;
import com.fi0x.deepmagic.items.DemonCrystal;
import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import com.fi0x.deepmagic.particlesystem.ParticleEnum;
import com.fi0x.deepmagic.particlesystem.ParticleSpawner;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class DemonStone extends BlockBase
{

    public DemonStone(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(3.0F);
        setHarvestLevel("pickaxe", 3);
        setLightLevel(1.0F);
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            if(playerIn.getHeldItem(hand).getItem() instanceof DemonCrystal)
            {
                PlayerMana playerMana = playerIn.getCapability(PlayerProperties.PLAYER_MANA, null);
                assert playerMana != null;
                if(!validateStructure(worldIn, pos)) return false;
                if(playerMana.removeMana(playerIn, ConfigHandler.demonSummonCost))
                {
                    playerIn.getHeldItem(hand).shrink(1);
                    playerMana.addSkillXP(playerIn, ConfigHandler.demonSummonXP);

                    EntityDemon demon = new EntityDemon(worldIn);
                    demon.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0);
                    worldIn.spawnEntity(demon);
                }
            }
        }
        return true;
    }
    @Override
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand)
    {
        if(rand.nextInt(100) + 1 > ConfigHandler.demonStoneParticles) return;
        if(!validateStructure(worldIn, pos)) return;

        int particles = 0;
        switch(Minecraft.getMinecraft().gameSettings.particleSetting)
        {
            case 0:
                particles = 1;
                break;
            case 1:
                particles = 2;
        }

        for(int i = 0; i < particles; i++)
        {
            double xCenter = pos.getX() + 0.5;
            double y = pos.getY() + 1;
            double zCenter = pos.getZ() + 0.5;

            double xOff = Math.random() * 3 - 1.5;
            double zOff = Math.random() * 3 - 1.5;

            ParticleSpawner.spawnParticle(ParticleEnum.DEMON_STONE, xCenter + xOff, y, zCenter + zOff);
        }
    }
    private boolean validateStructure(World world, BlockPos pos)
    {
        pos = pos.down();
        if(!ConfigHandler.requireDemonStructure) return true;
        return world.getBlockState(pos).getBlock().getUnlocalizedName().equals("tile.demon_crystal_block") &&
                world.getBlockState(pos.north()).getBlock().getUnlocalizedName().equals("tile.deep_crystal_block") &&
                world.getBlockState(pos.north().east()).getBlock().getUnlocalizedName().equals("tile.blockIron") &&
                world.getBlockState(pos.north().west()).getBlock().getUnlocalizedName().equals("tile.blockIron") &&
                world.getBlockState(pos.south()).getBlock().getUnlocalizedName().equals("tile.deep_crystal_block") &&
                world.getBlockState(pos.south().east()).getBlock().getUnlocalizedName().equals("tile.blockIron") &&
                world.getBlockState(pos.south().west()).getBlock().getUnlocalizedName().equals("tile.blockIron") &&
                world.getBlockState(pos.west()).getBlock().getUnlocalizedName().equals("tile.deep_crystal_block") &&
                world.getBlockState(pos.east()).getBlock().getUnlocalizedName().equals("tile.deep_crystal_block");
    }
}