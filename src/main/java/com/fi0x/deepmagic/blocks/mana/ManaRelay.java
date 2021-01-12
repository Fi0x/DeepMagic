package com.fi0x.deepmagic.blocks.mana;

import com.fi0x.deepmagic.blocks.BlockBase;
import com.fi0x.deepmagic.blocks.tileentity.TileEntityManaRelay;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.items.mana.ManaLinker;
import com.fi0x.deepmagic.particlesystem.ParticleEnum;
import com.fi0x.deepmagic.particlesystem.ParticleSpawner;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class ManaRelay extends BlockBase implements ITileEntityProvider
{
    public ManaRelay(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(5.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 0);
        setLightLevel(0.1F);
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        System.out.println("Block activated");
        if(worldIn.isRemote) return false;
        ItemStack stack = playerIn.getHeldItem(hand);
        Item item = stack.getItem();

        TileEntityManaRelay te = (TileEntityManaRelay) worldIn.getTileEntity(pos);
        assert te != null;

        if(item instanceof ManaLinker)
        {
            NBTTagCompound compound;
            if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
            compound = stack.getTagCompound();
            assert compound != null;

            if(compound.hasKey("x"))
            {

                int x = compound.getInteger("x");
                int y = compound.getInteger("y");
                int z = compound.getInteger("z");

                if(te.addOrRemoveTarget(new BlockPos(x, y, z))) playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Linked to " + x + ", " + y + ", " + z));
                else playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Unlinked " + x + ", " + y + ", " + z));
            } else
            {
                compound.setInteger("x", pos.getX());
                compound.setInteger("y", pos.getY());
                compound.setInteger("z", pos.getZ());
                playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Location stored"));
            }
        } else if(item.getUnlocalizedName().equals("dimensional_crystal"))
        {
            if(te.removeRangeLimit())
            {
                playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Range limit removed"));
                stack.shrink(1);
            }
        }
        return false;
    }
    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.MANA_RELAY);
    }
    @Nonnull
    @Override
    public ItemStack getItem(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
    {
        return new ItemStack(ModBlocks.MANA_RELAY);
    }
    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileEntityManaRelay();
    }
    @Override
    public boolean isOpaqueCube(@Nonnull IBlockState state)
    {
        return false;
    }
    @Override
    public boolean isFullCube(@Nonnull IBlockState state)
    {
        return false;
    }
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand)
    {
        if(rand.nextInt(4) > 0) return;
        if(((TileEntityManaRelay) Objects.requireNonNull(worldIn.getTileEntity(pos))).hasTargets())
        {
            double x = pos.getX() + (Math.random() * 0.2) + 0.4;
            double y = pos.getY() + 1 + (Math.random() * 0.2);
            double z = pos.getZ() + (Math.random() * 0.2) + 0.4;

            ParticleSpawner.spawnParticle(ParticleEnum.MANA_BLOCK, x, y, z, 0, 0, 0, Math.random() * 0.3 + 0.2, false, 16);
        }
    }
}