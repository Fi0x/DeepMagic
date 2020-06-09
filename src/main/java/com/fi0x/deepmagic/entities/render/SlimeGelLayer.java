package com.fi0x.deepmagic.entities.render;

import com.fi0x.deepmagic.entities.EntitySpikySlime;
import com.fi0x.deepmagic.entities.model.ModelSpikySlime;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public class SlimeGelLayer implements LayerRenderer<EntitySpikySlime>
{
    private final RenderSpikySlime slimeRenderer;
    private final ModelBase slimeModel = new ModelSpikySlime();

    public SlimeGelLayer(RenderSpikySlime slimeRenderer)
    {
        this.slimeRenderer = slimeRenderer;
    }

    @Override
    public void doRenderLayer(EntitySpikySlime entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entitylivingbaseIn.isInvisible())
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
            this.slimeModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
        }
    }
    @Override
    public boolean shouldCombineTextures()
    {
        return true;
    }
}