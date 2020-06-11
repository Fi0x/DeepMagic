package com.fi0x.deepmagic.blocks.insanity;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.util.IHasModel;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class InsanityLeaves extends BlockLeaves implements IHasModel
{
    public InsanityLeaves(String name)
    {
        super();
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(DeepMagicTab.BLOCKS);

        setSoundType(SoundType.PLANT);
        setHardness(0.3F);

        setDefaultState(getBlockState().getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }
    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
    @Override
    protected void dropApple(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, int chance)
    {
        if(worldIn.rand.nextInt(chance) == 0)
        {
            spawnAsEntity(worldIn, pos, new ItemStack(Items.APPLE));
        }
    }
    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.DEEP_CRYSTAL_BLOCK);
    }
    @Override
    public boolean isOpaqueCube(@Nonnull IBlockState state)
    {
        return false;
    }
    @Nonnull
    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Nonnull
    @Override
    protected ItemStack getSilkTouchDrop(@Nonnull IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this));
    }
    @Override
    public void harvestBlock(World worldIn, @Nonnull EntityPlayer player, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nullable TileEntity te, @Nonnull ItemStack stack)
    {
        if(!worldIn.isRemote && stack.getItem() == Items.SHEARS)
        {
            player.addStat(Objects.requireNonNull(StatList.getBlockStats(this)));
        } else
        {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }
    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        return NonNullList.withSize(1, new ItemStack(this));
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta)
    {
        return null;
    }
    @Override
    public void getSubBlocks(@Nonnull CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this));
    }
    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }
    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        if(!state.getValue(DECAYABLE).booleanValue())
        {
            i |= 4;
        }
        if(state.getValue(CHECK_DECAY).booleanValue())
        {
            i |= 8;
        }
        return i;
    }
    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE);
    }

    @Override
    public int damageDropped(@Nonnull IBlockState state)
    {
        return 0;
    }
}