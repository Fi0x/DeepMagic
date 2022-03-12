/**
 * Thaumic Augmentation
 * Copyright (c) 2019 TheCodex6824.
 * <p>
 * This file is part of Thaumic Augmentation.
 * <p>
 * Thaumic Augmentation is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Thaumic Augmentation is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with Thaumic Augmentation.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.fi0x.deepmagic.world.generators.dungeon.large;

import com.fi0x.deepmagic.util.Reference;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class LargeDungeonBaseComponent extends LargeDungeonComponent
{
    //TODO: Adapt code for dungeon
    public LargeDungeonBaseComponent()
    {
        super();
    }

    public LargeDungeonBaseComponent(Template template, String templateName, boolean fillBelow, BlockPos position, Rotation rot, Mirror mi)
    {
        super(template, templateName, fillBelow, position, rot, mi);
    }

    public LargeDungeonBaseComponent(TemplateManager templateManager, String templateName, boolean fillBelow, BlockPos position, Rotation rot, Mirror mi)
    {
        super(templateManager.get(FMLCommonHandler.instance().getMinecraftServerInstance(), new ResourceLocation(Reference.MOD_ID, templateName)), templateName, fillBelow, position, rot, mi);
    }

    @Override
    public void onPostGeneration(World world, StructureBoundingBox structurebb)
    {
        if(structurebb.intersectsWith(boundingBox))
        {
            int minY = boundingBox.minY;
            MutableBlockPos mutable = new MutableBlockPos();
            for(int x = structurebb.minX; x <= structurebb.maxX; ++x)
            {
                for(int z = structurebb.minZ; z <= structurebb.maxZ; ++z)
                {
                    mutable.setPos(x, minY, z);
                    if(boundingBox.isVecInside(mutable) && !world.isAirBlock(mutable))
                    {
                        for(int y = minY - 1; y >= 0; --y)
                        {
                            mutable.setY(y);
                            if(!world.isAirBlock(mutable)
                                    && !world.getBlockState(mutable).getMaterial().isLiquid())
                                break;
                        }
                    }
                }
            }
        }
    }

}
