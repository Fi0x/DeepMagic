package com.fi0x.deepmagic.world.generators.dungeon.large;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class LargeDungeonTemplate extends Template
{
    //TODO: Check if there is anything useful in this class
    protected Template wrapped;

    public LargeDungeonTemplate(Template wrappedTemplate)
    {
        wrapped = wrappedTemplate;
    }

    @Override
    public void addBlocksToWorld(@Nonnull World world, @Nonnull BlockPos pos, ITemplateProcessor templateProcessor, @Nonnull PlacementSettings placement, int flags)
    {
        super.addBlocksToWorld(world, pos, templateProcessor, placement, flags);
        //TODO: Check ThaumicAugmentation
    }

    @Nonnull
    @Override
    public BlockPos calculateConnectedPos(@Nonnull PlacementSettings placementIn, @Nonnull BlockPos p_186262_2_, @Nonnull PlacementSettings p_186262_3_, @Nonnull BlockPos p_186262_4_)
    {
        return wrapped.calculateConnectedPos(placementIn, p_186262_2_, p_186262_3_, p_186262_4_);
    }

    @Nonnull
    @Override
    public String getAuthor()
    {
        return wrapped.getAuthor();
    }

    @Nonnull
    @Override
    public Map<BlockPos, String> getDataBlocks(@Nonnull BlockPos pos, @Nonnull PlacementSettings placementIn)
    {
        return wrapped.getDataBlocks(pos, placementIn);
    }

    @Nonnull
    @Override
    public BlockPos getSize()
    {
        return wrapped.getSize();
    }

    @Nonnull
    @Override
    public BlockPos getZeroPositionWithTransform(@Nonnull BlockPos p_189961_1_, @Nonnull Mirror p_189961_2_, @Nonnull Rotation p_189961_3_)
    {
        return wrapped.getZeroPositionWithTransform(p_189961_1_, p_189961_2_, p_189961_3_);
    }

    @Override
    public void read(@Nonnull NBTTagCompound compound)
    {
        wrapped.read(compound);
    }

    @Override
    public void setAuthor(@Nonnull String authorIn)
    {
        wrapped.setAuthor(authorIn);
    }

    @Override
    public void takeBlocksFromWorld(@Nonnull World worldIn, @Nonnull BlockPos startPos, @Nonnull BlockPos size, boolean takeEntities, @Nullable Block toIgnore)
    {
        wrapped.takeBlocksFromWorld(worldIn, startPos, size, takeEntities, toIgnore);
    }

    @Nonnull
    @Override
    public BlockPos transformedSize(@Nonnull Rotation rotationIn)
    {
        return wrapped.transformedSize(rotationIn);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound nbt)
    {
        return wrapped.writeToNBT(nbt);
    }
}
