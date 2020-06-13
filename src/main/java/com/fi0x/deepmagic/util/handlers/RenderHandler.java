package com.fi0x.deepmagic.util.handlers;

import com.fi0x.deepmagic.entities.*;
import com.fi0x.deepmagic.entities.render.*;
import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import javax.annotation.Nonnull;

public class RenderHandler
{
    public static void registerEntityRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityInsanityCow.class, RenderInsanityCow::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHoveringOrb.class, RenderHoveringOrb::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpikySlime.class, RenderSpikySlime::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityNetherWorm.class, RenderNetherWorm::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGiant.class, RenderGiant::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRockTroll.class, RenderRockTroll::new);
    }

    public static void registerCustomMeshesAndStates()
    {
        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.INSANITY_WATER), new ItemMeshDefinition()
        {
            @Nonnull
            @Override
            public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack)
            {
                return new ModelResourceLocation("deepmagic:insanity_water", "fluid");
            }
        });

        ModelLoader.setCustomStateMapper(ModBlocks.INSANITY_WATER, new StateMapperBase()
        {
            @Nonnull
            @Override
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state)
            {
                return new ModelResourceLocation("deepmagic:insanity_water", "fluid");
            }
        });
    }
}