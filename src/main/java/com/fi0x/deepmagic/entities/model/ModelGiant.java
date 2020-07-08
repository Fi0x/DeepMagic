package com.fi0x.deepmagic.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

/**
 * ModelGiant - Either Mojang or a mod author
 * Created using Tabula 7.1.0
 */
public class ModelGiant extends ModelBase
{
    public ModelRenderer armRight;
    public ModelRenderer legRight;
    public ModelRenderer head;
    public ModelRenderer bodyUp;
    public ModelRenderer armLeft;
    public ModelRenderer legLeft;
    public ModelRenderer bodyDown;

    public ModelGiant()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.armLeft = new ModelRenderer(this, 0, 48);
        this.armLeft.mirror = true;
        this.armLeft.setRotationPoint(13.0F, 4.0F, 0.0F);
        this.armLeft.addBox(-0.0F, -2.0F, -4.0F, 8, 40, 8, 0.0F);
        this.legLeft = new ModelRenderer(this, 32, 66);
        this.legLeft.mirror = true;
        this.legLeft.setRotationPoint(6.0F, 6.0F, 0.1F);
        this.legLeft.addBox(-4.0F, 0.0F, -4.0F, 8, 26, 8, 0.0F);
        this.bodyUp = new ModelRenderer(this, 32, 34);
        this.bodyUp.setRotationPoint(0.0F, -28.0F, 0.0F);
        this.bodyUp.addBox(-13.0F, 0.0F, -6.0F, 26, 20, 12, 0.0F);
        this.bodyDown = new ModelRenderer(this, 64, 66);
        this.bodyDown.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.bodyDown.addBox(-8.0F, 0.0F, -5.0F, 16, 6, 10, 0.0F);
        this.head = new ModelRenderer(this, 64, 0);
        this.head.setRotationPoint(0.0F, 0F, -2.0F);
        this.head.addBox(-6.0F, -12.0F, -6.0F, 12, 12, 12, 0.0F);
        this.legRight = new ModelRenderer(this, 32, 0);
        this.legRight.setRotationPoint(-6.0F, 6.0F, 0.1F);
        this.legRight.addBox(-4.0F, 0.0F, -4.0F, 8, 26, 8, 0.0F);
        this.armRight = new ModelRenderer(this, 0, 0);
        this.armRight.setRotationPoint(-13.0F, 4.0F, 0.0F);
        this.armRight.addBox(-8.0F, -2.0F, -4.0F, 8, 40, 8, 0.0F);

        this.bodyUp.addChild(head);
        this.bodyUp.addChild(bodyDown);
        this.bodyDown.addChild(legLeft);
        this.bodyDown.addChild(legRight);
        this.bodyUp.addChild(armLeft);
        this.bodyUp.addChild(armRight);
    }

    @Override
    public void render(@Nonnull Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.bodyUp.render(f5);
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
