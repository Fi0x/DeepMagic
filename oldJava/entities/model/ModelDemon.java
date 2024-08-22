package com.fi0x.deepmagic.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

/**
 * ModelDemon - Fi0x
 * Created using Tabula 7.1.0
 */
public class ModelDemon extends ModelBase
{
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer armLeft;
    public ModelRenderer armRight;
    public ModelRenderer legLeft;
    public ModelRenderer legRight;
    public ModelRenderer tentacleThick1;
    public ModelRenderer tentacleThick2;
    public ModelRenderer tentacleMiddle1;
    public ModelRenderer tentacleMiddle2;
    public ModelRenderer tentacleThin1;
    public ModelRenderer tentacleThin2;
    public ModelRenderer tentacleTip1;
    public ModelRenderer tentacleTip2;
    public ModelRenderer hornLeft;
    public ModelRenderer hornRight;

    public ModelDemon()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.head = new ModelRenderer(this, 24, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.tentacleThin1 = new ModelRenderer(this, 56, 0);
        this.tentacleThin1.setRotationPoint(0F, 7.5F, 0F);
        this.tentacleThin1.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(tentacleThin1, 1F, 0.0F, 0.0F);
        this.tentacleThin2 = new ModelRenderer(this, 48, 16);
        this.tentacleThin2.setRotationPoint(0F, 7.5F, 0F);
        this.tentacleThin2.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(tentacleThin2, 1F, 0.0F, 0.0F);
        this.armLeft = new ModelRenderer(this, 0, 16);
        this.armLeft.setRotationPoint(5.0F, 1.0F, 0.0F);
        this.armLeft.addBox(-1.0F, -1.0F, -2.0F, 4, 12, 4, 0.0F);
        this.tentacleMiddle2 = new ModelRenderer(this, 16, 32);
        this.tentacleMiddle2.setRotationPoint(0F, 7.5F, 0F);
        this.tentacleMiddle2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(tentacleMiddle2, 1F, 0.0F, 0.0F);
        this.hornLeft = new ModelRenderer(this, 0, 0);
        this.hornLeft.setRotationPoint(2.0F, -8.0F, -2.0F);
        this.hornLeft.addBox(-0.5F, -1.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(hornLeft, 0.5235987755982988F, 0.0F, 0.0F);
        this.hornRight = new ModelRenderer(this, 20, 0);
        this.hornRight.setRotationPoint(-2.0F, -8.0F, -2.0F);
        this.hornRight.addBox(-0.5F, -1.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(hornRight, 0.5235987755982988F, 0.0F, 0.0F);
        this.tentacleTip1 = new ModelRenderer(this, 24, 32);
        this.tentacleTip1.setRotationPoint(0F, 7.5F, 0F);
        this.tentacleTip1.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(tentacleTip1, 0.9F, 0.0F, 0.0F);
        this.tentacleThick1 = new ModelRenderer(this, 54, 14);
        this.tentacleThick1.setRotationPoint(2.0F, 2.0F, 2.0F);
        this.tentacleThick1.addBox(-1.0F, -1.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(tentacleThick1, 1.05F, 0.2F, 0.0F);
        this.tentacleThick2 = new ModelRenderer(this, 0, 32);
        this.tentacleThick2.setRotationPoint(-2.0F, 2.0F, 2.0F);
        this.tentacleThick2.addBox(-1.0F, -1.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(tentacleThick2, 1.05F, -0.2F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.legLeft = new ModelRenderer(this, 32, 16);
        this.legLeft.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.legLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.tentacleTip2 = new ModelRenderer(this, 28, 32);
        this.tentacleTip2.setRotationPoint(0F, 7.5F, 0F);
        this.tentacleTip2.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(tentacleTip2, 0.9F, 0.0F, 0.0F);
        this.armRight = new ModelRenderer(this, 16, 16);
        this.armRight.setRotationPoint(-5.0F, 1.0F, 0.0F);
        this.armRight.addBox(-3.0F, -1.0F, -2.0F, 4, 12, 4, 0.0F);
        this.legRight = new ModelRenderer(this, 44, 28);
        this.legRight.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.legRight.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.tentacleMiddle1 = new ModelRenderer(this, 8, 32);
        this.tentacleMiddle1.setRotationPoint(0F, 7.5F, 0F);
        this.tentacleMiddle1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(tentacleMiddle1, 1F, 0.0F, 0.0F);

        this.body.addChild(legLeft);
        this.body.addChild(legRight);
        this.body.addChild(armLeft);
        this.body.addChild(armRight);
        this.body.addChild(head);
        this.head.addChild(hornLeft);
        this.head.addChild(hornRight);
        this.body.addChild(tentacleThick1);
        this.body.addChild(tentacleThick2);
        this.tentacleThick1.addChild(tentacleMiddle1);
        this.tentacleThick2.addChild(tentacleMiddle2);
        this.tentacleMiddle1.addChild(tentacleThin1);
        this.tentacleMiddle2.addChild(tentacleThin2);
        this.tentacleThin1.addChild(tentacleTip1);
        this.tentacleThin2.addChild(tentacleTip2);
    }

    @Override
    public void render(@Nonnull Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.body.render(f5);
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
