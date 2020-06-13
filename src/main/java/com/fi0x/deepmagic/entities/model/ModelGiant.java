package com.fi0x.deepmagic.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

/**
 * ModelZombie - Either Mojang or a mod author
 * Created using Tabula 7.1.0
 */
public class ModelGiant extends ModelBase {
    public ModelRenderer armRight;
    public ModelRenderer legRight;
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer armLeft;
    public ModelRenderer legLeft;

    public ModelGiant() {
        this.textureWidth = 512;
        this.textureHeight = 256;
        this.head = new ModelRenderer(this, 192, 0);
        this.head.setRotationPoint(0.0F, -120.0F, 0.0F);
        this.head.addBox(-24.0F, -48.0F, -24.0F, 48, 48, 48, 0.0F);
        this.armRight = new ModelRenderer(this, 0, 0);
        this.armRight.setRotationPoint(-24.0F, -108.0F, 0.0F);
        this.armRight.addBox(-22.0F, -12.0F, -12.0F, 24, 72, 24, 0.0F);
        this.setRotateAngle(armRight, -1.3962634015954636F, -0.10000736613927509F, 0.10000736613927509F);
        this.legRight = new ModelRenderer(this, 96, 0);
        this.legRight.setRotationPoint(-11.9F, -48.0F, 0.1F);
        this.legRight.addBox(-12.0F, 0.0F, -12.0F, 24, 72, 24, 0.0F);
        this.legLeft = new ModelRenderer(this, 96, 96);
        this.legLeft.mirror = true;
        this.legLeft.setRotationPoint(11.9F, -48.0F, 0.1F);
        this.legLeft.addBox(-12.0F, 0.0F, -12.0F, 24, 72, 24, 0.0F);
        this.armLeft = new ModelRenderer(this, 0, 96);
        this.armLeft.mirror = true;
        this.armLeft.setRotationPoint(24.0F, -108.0F, 0.0F);
        this.armLeft.addBox(-2.0F, -12.0F, -12.0F, 24, 72, 24, 0.0F);
        this.setRotateAngle(armLeft, -1.3962634015954636F, 0.10000736613927509F, -0.10000736613927509F);
        this.body = new ModelRenderer(this, 360, 72);
        this.body.setRotationPoint(0.0F, -120.0F, 0.0F);
        this.body.addBox(-24.0F, 0.0F, -12.0F, 48, 72, 24, 0.0F);
    }

    @Override
    public void render(@Nonnull Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.head.render(f5);
        this.armRight.render(f5);
        this.legRight.render(f5);
        this.legLeft.render(f5);
        this.armLeft.render(f5);
        this.body.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, @Nonnull Entity entityIn)
    {
        this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.25F) * 1F * limbSwingAmount;
        this.legRight.rotateAngleX = MathHelper.cos(limbSwing * 0.25F + (float) Math.PI) * 1F * limbSwingAmount;

        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;

        float f = MathHelper.sin(this.swingProgress * (float)Math.PI);
        float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
        this.armRight.rotateAngleZ = 0.0F;
        this.armLeft.rotateAngleZ = 0.0F;
        this.armRight.rotateAngleY = -(0.1F - f * 0.6F);
        this.armLeft.rotateAngleY = 0.1F - f * 0.6F;
        float f2 = -(float) Math.PI / 2.25F;
        this.armRight.rotateAngleX = f2;
        this.armLeft.rotateAngleX = f2;
        this.armRight.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.armLeft.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.armRight.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.armLeft.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.armRight.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.armLeft.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }
}
