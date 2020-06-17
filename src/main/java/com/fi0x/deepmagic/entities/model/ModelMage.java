package com.fi0x.deepmagic.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelMage - Fi0x
 * Created using Tabula 7.1.0
 */
public class ModelMage extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer legLeft;
    public ModelRenderer legRight;
    public ModelRenderer armLeft;
    public ModelRenderer armRight;
    public ModelRenderer head;
    public ModelRenderer cap1;
    public ModelRenderer cap2;
    public ModelRenderer cap3;
    public ModelRenderer cap4;
    public ModelRenderer cap5;
    public ModelRenderer cap6;

    public ModelMage() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.legRight = new ModelRenderer(this, 40, 0);
        this.legRight.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.legRight.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.cap1 = new ModelRenderer(this, 0, 40);
        this.cap1.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.cap1.addBox(-5.0F, -1.0F, -5.0F, 10, 1, 10, 0.0F);
        this.cap2 = new ModelRenderer(this, 30, 40);
        this.cap2.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.cap2.addBox(-4.0F, -1.0F, -4.0F, 8, 1, 8, 0.0F);
        this.cap6 = new ModelRenderer(this, 0, 0);
        this.cap6.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.cap6.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.cap5 = new ModelRenderer(this, 20, 0);
        this.cap5.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.cap5.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
        this.armRight = new ModelRenderer(this, 16, 16);
        this.armRight.setRotationPoint(-5.0F, 1.0F, 0.0F);
        this.armRight.addBox(-3.0F, -1.0F, -2.0F, 4, 12, 4, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.legLeft = new ModelRenderer(this, 24, 0);
        this.legLeft.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.legLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.head = new ModelRenderer(this, 24, 24);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.cap3 = new ModelRenderer(this, 32, 16);
        this.cap3.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.cap3.addBox(-3.0F, -1.0F, -3.0F, 6, 1, 6, 0.0F);
        this.armLeft = new ModelRenderer(this, 0, 16);
        this.armLeft.setRotationPoint(5.0F, 1.0F, 0.0F);
        this.armLeft.addBox(-1.0F, -1.0F, -2.0F, 4, 12, 4, 0.0F);
        this.cap4 = new ModelRenderer(this, 0, 32);
        this.cap4.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.cap4.addBox(-2.0F, -2.0F, -2.0F, 4, 2, 4, 0.0F);

        this.body.addChild(legLeft);
        this.body.addChild(legRight);
        this.body.addChild(armLeft);
        this.body.addChild(armRight);
        this.body.addChild(head);
        this.head.addChild(cap1);
        this.cap1.addChild(cap2);
        this.cap2.addChild(cap3);
        this.cap3.addChild(cap4);
        this.cap4.addChild(cap5);
        this.cap5.addChild(cap6);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
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
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * 2F) * 1F * limbSwingAmount;
        this.legRight.rotateAngleX = MathHelper.cos(limbSwing * 2F + (float) Math.PI) * 1F * limbSwingAmount;
    }
}
