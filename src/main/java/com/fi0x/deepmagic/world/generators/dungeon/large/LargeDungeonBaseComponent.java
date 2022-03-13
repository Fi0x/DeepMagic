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
