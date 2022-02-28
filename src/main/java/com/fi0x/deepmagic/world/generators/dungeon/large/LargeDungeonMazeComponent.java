package com.fi0x.deepmagic.world.generators.dungeon.large;

import com.fi0x.deepmagic.util.Reference;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class LargeDungeonMazeComponent extends LargeDungeonComponent
{
    //TODO: Adapt code for dungeon
    public LargeDungeonMazeComponent()
    {
        super();
    }

    public LargeDungeonMazeComponent(Template template, String templateName, boolean fillBelow, BlockPos position, Rotation rot, Mirror mi)
    {
        super(template, templateName, fillBelow, position, rot, mi);
    }

    public LargeDungeonMazeComponent(TemplateManager templateManager, String templateName, boolean fillBelow, BlockPos position, Rotation rot, Mirror mi)
    {
        super(templateManager.get(FMLCommonHandler.instance().getMinecraftServerInstance(), new ResourceLocation(Reference.MOD_ID, templateName)), templateName, fillBelow, position, rot, mi);
    }

    @Override
    public boolean addComponentParts(@Nonnull World world, @Nonnull Random random, @Nonnull StructureBoundingBox bb)
    {
        placeSettings.setBoundingBox(bb);
        template.addBlocksToWorld(world, templatePosition, new MazeTemplateProcessor(templatePosition, placeSettings), placeSettings, 18);
        Map<BlockPos, String> map = template.getDataBlocks(templatePosition, placeSettings);
        for(Entry<BlockPos, String> entry : map.entrySet())
        {
            String s = entry.getValue();
            handleDataMarker(s, entry.getKey(), world, random, bb);
        }

        return true;
    }

    public static class MazeTemplateProcessor extends TemplateProcessor
    {

        public MazeTemplateProcessor(BlockPos pos, PlacementSettings settings)
        {
            super(pos, settings);
        }

        @Override
        @Nullable
        public BlockInfo processBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockInfo blockInfo)
        {
            BlockInfo info = super.processBlock(world, pos, blockInfo);
            if(info != null && info.tileentityData == null && info.blockState.getBlock() == BlocksTC.stoneAncient && rand.nextInt(4) == 0)
                info = new BlockInfo(info.pos, TABlocks.STONE.getDefaultState().withProperty(ITAStoneType.STONE_TYPE, StoneType.ANCIENT_RUNES), null);

            return info;
        }

    }

}
