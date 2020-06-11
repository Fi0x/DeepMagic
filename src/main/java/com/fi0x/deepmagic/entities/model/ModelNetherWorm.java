package com.fi0x.deepmagic.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;

/**
 * ModelNetherWorm - Fi0x
 * Created using Tabula 7.1.0
 */
public class ModelNetherWorm extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer eyeLeft;
    public ModelRenderer eyeRight;
    public ModelRenderer mouth;
    public ModelRenderer neck1;
    public ModelRenderer body1;

    public ModelNetherWorm() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.mouth = new ModelRenderer(this, 58, 0);
        this.mouth.setRotationPoint(0.0F, 15.0F, -26.0F);
        this.mouth.addBox(-6.0F, -16.0F, 9.0F, 12, 8, 1, 0.0F);
        this.neck1 = new ModelRenderer(this, 84, 0);
        this.neck1.setRotationPoint(0.0F, 14.0F, -8.0F);
        this.neck1.addBox(-4.0F, -18.0F, 8.0F, 8, 8, 2, 0.0F);
        this.eyeLeft = new ModelRenderer(this, 0, 0);
        this.eyeLeft.setRotationPoint(4.0F, 9.0F, -26.0F);
        this.eyeLeft.addBox(-1.5F, -15.5F, 8.0F, 3, 3, 2, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 14.0F, -10.0F);
        this.head.addBox(-8.0F, -8.0F, -16.0F, 16, 16, 16, 0.0F);
        this.body1 = new ModelRenderer(this, 48, 16);
        this.body1.setRotationPoint(0.0F, 14.0F, 0.0F);
        this.body1.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
        this.eyeRight = new ModelRenderer(this, 48, 0);
        this.eyeRight.setRotationPoint(-4.0F, 9.0F, -26.0F);
        this.eyeRight.addBox(-1.5F, -15.5F, 8.0F, 3, 3, 2, 0.0F);

        this.head.addChild(eyeLeft);
        this.head.addChild(eyeRight);
        this.head.addChild(mouth);
        this.head.addChild(neck1);
    }

    @Override
    public void render(@Nonnull Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.head.render(f5);
        this.body1.render(f5);
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
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
    }
}
