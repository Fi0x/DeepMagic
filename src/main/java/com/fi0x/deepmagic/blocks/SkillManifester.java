package com.fi0x.deepmagic.blocks;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class SkillManifester extends BlockBase
{
    public SkillManifester(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(3.0F);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            if(playerIn.getHeldItemMainhand().getItem() == ModItems.SKILLPOINT_UNCHARGED)
            {
                if(playerIn.getCapability(PlayerProperties.PLAYER_MANA, null).removeSkillpoint())
                {
                    playerIn.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ModItems.SKILLPOINT_CHARGED));
                } else
                {
                    playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have any unbound skillpoints"));
                }
            }
        }
        return true;
    }
}