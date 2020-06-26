package com.fi0x.deepmagic.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

/**
 * ModelDwarf - Fi0x
 * Created using Tabula 7.1.0
 */
public class ModelDwarf extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer legLeft;
    public ModelRenderer legRight;
    public ModelRenderer head;
    public ModelRenderer armLeft;
    public ModelRenderer armRight;

    public ModelDwarf() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.legLeft = new ModelRenderer(this, 18, 0);
        this.legLeft.setRotationPoint(1.5F, 6.0F, 0.0F);
        this.legLeft.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        this.legRight = new ModelRenderer(this, 30, 0);
        this.legRight.setRotationPoint(-1.5F, 6.0F, 0.0F);
        this.legRight.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        this.head = new ModelRenderer(this, 37, 3);
        this.head.setRotationPoint(0.0F, 0F, 0.0F);
        this.head.addBox(-3.0F, -5.0F, -2.5F, 6, 5, 5, 0.0F);
        this.armLeft = new ModelRenderer(this, 15, 8);
        this.armLeft.setRotationPoint(3.0F, 0F, 0.0F);
        this.armLeft.addBox(0.0F, 0.0F, -1.5F, 2, 6, 3, 0.0F);
        this.armRight = new ModelRenderer(this, 25, 8);
        this.armRight.setRotationPoint(-3.0F, 0F, 0.0F);
        this.armRight.addBox(-2.0F, 0.0F, -1.5F, 2, 6, 3, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 13.0F, 0.0F);
        this.body.addBox(-3.0F, 0.0F, -1.5F, 6, 6, 3, 0.0F);

        body.addChild(head);
        body.addChild(armLeft);
        body.addChild(armRight);
        body.addChild(legLeft);
        body.addChild(legRight);
    }

    @Override
    public void render(@Nonnull Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
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
        this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * 1F) * 1F * limbSwingAmount;
        this.legRight.rotateAngleX = MathHelper.cos(limbSwing * 1F + (float) Math.PI) * 1F * limbSwingAmount;

        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;

        float f = MathHelper.sin(this.swingProgress * (float)Math.PI);
        float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
        this.armRight.rotateAngleZ = 0.0F;
        this.armLeft.rotateAngleZ = 0.0F;
        this.armRight.rotateAngleY = -(0.1F - f * 0.6F);
        this.armLeft.rotateAngleY = 0.1F - f * 0.6F;
        this.armRight.rotateAngleX = 0;
        this.armLeft.rotateAngleX = 0;
        this.armRight.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.armLeft.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.armRight.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.armLeft.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.armRight.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.armLeft.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }
}
