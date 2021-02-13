package com.fi0x.deepmagic.blocks.mana;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.blocks.mana.tile.TileEntitySpellStone;
import com.fi0x.deepmagic.blocks.tileentity.BlockTileEntity;
import com.fi0x.deepmagic.particlesystem.ParticleEnum;
import com.fi0x.deepmagic.particlesystem.ParticleSpawner;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.world.StructureChecker;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class SpellStone extends BlockTileEntity<TileEntitySpellStone>
{
    public SpellStone(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(5.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote && StructureChecker.verifySpellStoneStructure(worldIn, pos)) playerIn.openGui(Main.instance, ConfigHandler.guiSpellStoneID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
    @Override
    public void breakBlock(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
    {
        TileEntitySpellStone te = (TileEntitySpellStone) worldIn.getTileEntity(pos);
        assert te != null;
        InventoryHelper.dropInventoryItems(worldIn, pos, te);
        super.breakBlock(worldIn, pos, state);
    }
    @Override
    public Class<TileEntitySpellStone> getTileEntityClass()
    {
        return TileEntitySpellStone.class;
    }
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntitySpellStone();
    }
    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand)
    {
        if(rand.nextInt(100) + 1 > ConfigHandler.spellStoneParticles) return;

        TileEntitySpellStone tile = getTileEntity(worldIn, pos);
        if(tile.getField(0) <= 0) return;

        int particles = 0;
        switch(Minecraft.getMinecraft().gameSettings.particleSetting)
        {
            case 0:
                particles = 2;
                break;
            case 1:
                particles = 4;
                break;
        }

        for(int i = 0; i < particles; i++)
        {
            double x = pos.getX() + Math.random();
            double y = pos.getY() + 1;
            double z = pos.getZ() + Math.random();

            ParticleSpawner.spawnParticle(ParticleEnum.SPELL_STONE, x, y, z);
        }
    }
}