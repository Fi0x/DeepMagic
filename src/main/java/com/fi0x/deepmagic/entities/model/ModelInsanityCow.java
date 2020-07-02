package com.fi0x.deepmagic.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

/**
 * ModelCow - Either Mojang or a mod author
 * Created using Tabula 7.1.0
 */
public class ModelInsanityCow extends ModelBase
{
    public ModelRenderer body;
    public ModelRenderer tailThinLeft;
    public ModelRenderer tailThinRight;
    public ModelRenderer tailThickRight;
    public ModelRenderer tailThickLeft;
    public ModelRenderer wingRight;
    public ModelRenderer wingLeft;
    public ModelRenderer legFrontRight;
    public ModelRenderer legFrontLeft;
    public ModelRenderer legBackRight;
    public ModelRenderer legBackLeft;
    public ModelRenderer rightHead;
    public ModelRenderer rightHeadEar1;
    public ModelRenderer rightHeadEar2;
    public ModelRenderer leftHead;
    public ModelRenderer leftHeadEar1;
    public ModelRenderer leftHeadEar2;

    public ModelInsanityCow()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.rightHead = new ModelRenderer(this, 0, 48);
        this.rightHead.setRotationPoint(-3.8F, 4.0F, -8.0F);
        this.rightHead.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
        this.leftHeadEar1 = new ModelRenderer(this, 0, 0);
        this.leftHeadEar1.setRotationPoint(0F, 0F, 0F);
        this.leftHeadEar1.addBox(3.5F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.legBackRight = new ModelRenderer(this, 42, 0);
        this.legBackRight.setRotationPoint(-4.0F, 7.0F, 6.0F);
        this.legBackRight.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.body = new ModelRenderer(this, 0, 2);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
        this.body.addBox(-6.0F, -3.0F, -10.0F, 12, 10, 18, 0.0F);
        this.tailThickLeft = new ModelRenderer(this, 48, 30);
        this.tailThickLeft.setRotationPoint(2.0F, -2.5F, 7.5F);
        this.tailThickLeft.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(tailThickLeft, 1.0471975511965976F, 0.0F, 0.0F);
        this.leftHead = new ModelRenderer(this, 28, 48);
        this.leftHead.setRotationPoint(3.8F, 4.0F, -8.0F);
        this.leftHead.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
        this.legFrontLeft = new ModelRenderer(this, 0, 32);
        this.legFrontLeft.setRotationPoint(4.0F, 7.0F, -8.0F);
        this.legFrontLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.rightHeadEar1 = new ModelRenderer(this, 8, 0);
        this.rightHeadEar1.setRotationPoint(0F, 0F, 0F);
        this.rightHeadEar1.addBox(3.5F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.legFrontRight = new ModelRenderer(this, 16, 32);
        this.legFrontRight.setRotationPoint(-4.0F, 7.0F, -8.0F);
        this.legFrontRight.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.tailThickRight = new ModelRenderer(this, 52, 30);
        this.tailThickRight.setRotationPoint(-2.0F, -2.5F, 7.5F);
        this.tailThickRight.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(tailThickRight, 1.0471975511965976F, 0.0F, 0.0F);
        this.leftHeadEar2 = new ModelRenderer(this, 0, 10);
        this.leftHeadEar2.setRotationPoint(0F, 0F, 0F);
        this.leftHeadEar2.addBox(-4.5F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.legBackLeft = new ModelRenderer(this, 32, 32);
        this.legBackLeft.setRotationPoint(4.0F, 7.0F, 6.0F);
        this.legBackLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.wingLeft = new ModelRenderer(this, 0, 10);
        this.wingLeft.setRotationPoint(6.0F, -2.8F, -4.0F);
        this.wingLeft.addBox(0.0F, 0.0F, 0.0F, 1, 4, 6, 0.0F);
        this.setRotateAngle(wingLeft, 0.0F, 0.0F, -0.3490658503988659F);
        this.wingRight = new ModelRenderer(this, 0, 0);
        this.wingRight.setRotationPoint(-6.0F, -2.8F, -4.0F);
        this.wingRight.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 6, 0.0F);
        this.setRotateAngle(wingRight, 0.0F, 0.0F, 0.3490658503988659F);
        this.tailThinRight = new ModelRenderer(this, 48, 39);
        this.tailThinRight.setRotationPoint(0.0F, 8F, 0.0F);
        this.tailThinRight.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(tailThinRight, 1.0F, 0.0F, 0.0F);
        this.tailThinLeft = new ModelRenderer(this, 52, 39);
        this.tailThinLeft.setRotationPoint(0.0F, 8F, 0F);
        this.tailThinLeft.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(tailThinLeft, 1.0F, 0.0F, 0.0F);
        this.rightHeadEar2 = new ModelRenderer(this, 8, 10);
        this.rightHeadEar2.setRotationPoint(0F, 0F, 0F);
        this.rightHeadEar2.addBox(-4.500000000000002F, -5.0F, -4.0F, 1, 3, 1, 0.0F);

        this.body.addChild(legBackLeft);
        this.body.addChild(legBackRight);
        this.body.addChild(legFrontLeft);
        this.body.addChild(legFrontRight);
        this.body.addChild(wingLeft);
        this.body.addChild(wingRight);
        this.body.addChild(tailThickLeft);
        this.body.addChild(tailThickRight);
        this.tailThickLeft.addChild(tailThinLeft);
        this.tailThickRight.addChild(tailThinRight);
        this.leftHead.addChild(leftHeadEar1);
        this.leftHead.addChild(leftHeadEar2);
        this.rightHead.addChild(rightHeadEar1);
        this.rightHead.addChild(rightHeadEar2);
    }

    @Override
    public void render(@Nonnull Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.body.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.rightHead.offsetX, this.rightHead.offsetY, this.rightHead.offsetZ);
        GlStateManager.translate(this.rightHead.rotationPointX * f5, this.rightHead.rotationPointY * f5, this.rightHead.rotationPointZ * f5);
        GlStateManager.scale(0.9D, 0.9D, 0.9D);
        GlStateManager.translate(-this.rightHead.offsetX, -this.rightHead.offsetY, -this.rightHead.offsetZ);
        GlStateManager.translate(-this.rightHead.rotationPointX * f5, -this.rightHead.rotationPointY * f5, -this.rightHead.rotationPointZ * f5);
        this.rightHead.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.leftHead.offsetX, this.leftHead.offsetY, this.leftHead.offsetZ);
        GlStateManager.translate(this.leftHead.rotationPointX * f5, this.leftHead.rotationPointY * f5, this.leftHead.rotationPointZ * f5);
        GlStateManager.scale(0.9D, 0.9D, 0.9D);
        GlStateManager.translate(-this.leftHead.offsetX, -this.leftHead.offsetY, -this.leftHead.offsetZ);
        GlStateManager.translate(-this.leftHead.rotationPointX * f5, -this.leftHead.rotationPointY * f5, -this.leftHead.rotationPointZ * f5);
        this.leftHead.render(f5);
        GlStateManager.popMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, @Nonnull Entity entityIn)
    {
        this.legFrontLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legBackRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legFrontRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legBackLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

        this.wingLeft.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.wingRight.rotateAngleZ = -MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        this.leftHead.rotateAngleY = netHeadYaw * 0.017453292F;
        this.rightHead.rotateAngleY = netHeadYaw * 0.017453292F;
        this.leftHead.rotateAngleX = headPitch * 0.017453292F;
        this.rightHead.rotateAngleX = headPitch * 0.017453292F;
    }
}
