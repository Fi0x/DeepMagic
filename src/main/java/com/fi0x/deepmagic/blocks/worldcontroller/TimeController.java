package com.fi0x.deepmagic.blocks.worldcontroller;

import com.fi0x.deepmagic.blocks.BlockBase;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Objects;

public class TimeController extends BlockBase
{
    public TimeController(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.METAL);
        setHardness(3.0F);
        setHarvestLevel("pickaxe", 1);
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            if(Objects.requireNonNull(playerIn.getCapability(PlayerProperties.PLAYER_MANA, null)).removeMana(100)) worldIn.setWorldTime(worldIn.getWorldTime() + 1000);
            else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have enough Mana"));
        }
        return true;
    }
}