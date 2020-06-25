package com.fi0x.deepmagic.blocks;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class AltarOfKnowledge extends BlockBase
{
    public AltarOfKnowledge(String name, Material material)
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
//            PacketHandler.INSTANCE.sendToServer(new PacketGetSkill(playerIn.getName(), playerMana.getSkillXP(), playerMana.getSkillpoints(), playerMana.getManaRegenRate(), playerMana.getManaEfficiency(), playerMana.maxManaMultiplier, playerMana.addedHP, playerMana.hpRegeneration));
            Main.proxy.openSkilltreeGui(playerIn.getCapability(PlayerProperties.PLAYER_MANA, null));
        }
        return true;
    }
}