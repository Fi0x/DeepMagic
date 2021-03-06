package com.fi0x.deepmagic.blocks;

import com.fi0x.deepmagic.entities.mobs.EntityDemon;
import com.fi0x.deepmagic.items.DemonCrystal;
import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.world.StructureChecker;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

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
                if(!StructureChecker.verifyDemonStructure(worldIn, pos)) return false;
                if(playerMana.removeMana(playerIn, ConfigHandler.demonSummonCost, true))
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
}