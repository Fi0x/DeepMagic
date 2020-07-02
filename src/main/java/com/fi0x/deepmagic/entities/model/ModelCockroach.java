package com.fi0x.deepmagic.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

/**
 * ModelCockroach - Fi0x
 * Created using Tabula 7.1.0
 */
public class ModelCockroach extends ModelBase
{
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;
    public ModelRenderer leg5;
    public ModelRenderer leg6;

    public ModelCockroach() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.leg1 = new ModelRenderer(this, 22, 0);
        this.leg1.setRotationPoint(3.0F, 1.0F, -4.0F);
        this.leg1.addBox(0.0F, 0.0F, -0.5F, 3, 1, 1, 0.0F);
        this.leg4 = new ModelRenderer(this, 46, 0);
        this.leg4.setRotationPoint(-3.0F, 1.0F, -4.0F);
        this.leg4.addBox(-3.0F, 0.0F, -0.5F, 3, 1, 1, 0.0F);
        this.leg6 = new ModelRenderer(this, 22, 2);
        this.leg6.setRotationPoint(-3.0F, 1.0F, 3.0F);
        this.leg6.addBox(-3.0F, 0.0F, -0.5F, 3, 1, 1, 0.0F);
        this.leg3 = new ModelRenderer(this, 38, 0);
        this.leg3.setRotationPoint(3.0F, 1.0F, 3.0F);
        this.leg3.addBox(0.0F, 0.0F, -0.5F, 3, 1, 1, 0.0F);
        this.leg2 = new ModelRenderer(this, 30, 0);
        this.leg2.setRotationPoint(3.0F, 1.0F, -0.5F);
        this.leg2.addBox(0.0F, 0.0F, -0.5F, 3, 1, 1, 0.0F);
        this.leg5 = new ModelRenderer(this, 54, 0);
        this.leg5.setRotationPoint(-3.0F, 1.0F, -0.5F);
        this.leg5.addBox(-3.0F, 0.0F, -0.5F, 3, 1, 1, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.body.addBox(-3.0F, 0.0F, -5.0F, 6, 2, 10, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.5F, -5.0F);
        this.head.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2, 0.0F);

        body.addChild(head);
        body.addChild(leg1);
        body.addChild(leg2);
        body.addChild(leg3);
        body.addChild(leg4);
        body.addChild(leg5);
        body.addChild(leg6);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, @Nonnull Entity entityIn)
    {
        this.leg1.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg5.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg6.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
    }
}
