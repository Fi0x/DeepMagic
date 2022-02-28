/**
 *  Thaumic Augmentation
 *  Copyright (c) 2019 TheCodex6824.
 *
 *  This file is part of Thaumic Augmentation.
 *
 *  Thaumic Augmentation is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Thaumic Augmentation is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Thaumic Augmentation.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.fi0x.deepmagic.world.generators.dungeon.large;

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

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

public class LargeDungeonMazeComponent extends LargeDungeonComponent
{
    //TODO: Fix code below (Check out EldritchSpire from ThaumicAugmentation)
    // need default constructor for loading by minecraft
    public LargeDungeonMazeComponent() {
        super();
    }
    
    public LargeDungeonMazeComponent(TemplateManager templateManager, Template template, String templateName,
                                     boolean fillBelow, BlockPos position, Rotation rot, Mirror mi, UUID wardOwner) {
        
        super(templateManager, template, templateName, fillBelow, position, rot, mi, wardOwner);
    }
    
    public LargeDungeonMazeComponent(TemplateManager templateManager, String templateName, boolean fillBelow,
                                     BlockPos position, Rotation rot, Mirror mi, UUID wardOwner) {
        
        super(templateManager, templateManager.get(FMLCommonHandler.instance().getMinecraftServerInstance(),
                new ResourceLocation(ThaumicAugmentationAPI.MODID, templateName)), templateName,
                fillBelow, position, rot, mi, wardOwner);
    }
    
    @Override
    public boolean addComponentParts(World world, Random random, StructureBoundingBox bb) {
        placeSettings.setBoundingBox(bb);
        ((EldritchSpireTemplate) template).addBlocksToWorld(world, templatePosition,
                new MazeTemplateProcessor(templatePosition, placeSettings), placeSettings, 18, ward);
        Map<BlockPos, String> map = template.getDataBlocks(templatePosition, placeSettings);
        for (Entry<BlockPos, String> entry : map.entrySet()) {
            String s = entry.getValue();
            handleDataMarker(s, entry.getKey(), world, random, bb);
        }

        return true;
    }
    
    public static class MazeTemplateProcessor extends TemplateProcessor {
        
        public MazeTemplateProcessor(BlockPos pos, PlacementSettings settings) {
            super(pos, settings);
        }
        
        @Override
        @Nullable
        public BlockInfo processBlock(World world, BlockPos pos, BlockInfo blockInfo) {
            BlockInfo info = super.processBlock(world, pos, blockInfo);
            if (info != null && info.tileentityData == null && info.blockState.getBlock() == BlocksTC.stoneAncient &&
                    rand.nextInt(4) == 0) {
                
                info = new BlockInfo(info.pos, TABlocks.STONE.getDefaultState().withProperty(ITAStoneType.STONE_TYPE,
                        StoneType.ANCIENT_RUNES), null);
            }
            
            return info;
        }
        
    }
    
}
