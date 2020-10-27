package com.fi0x.deepmagic.entities.model;

import com.fi0x.deepmagic.entities.mobs.EntityRockTroll;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

/**
 * ModelTroll - Fi0x
 * Created using Tabula 7.1.0
 */
public class ModelRockTroll extends ModelBase
{
    public ModelRenderer body;
    public ModelRenderer legLeft;
    public ModelRenderer legRight;
    public ModelRenderer head;
    public ModelRenderer armLeft;
    public ModelRenderer armRight;
    public ModelRenderer stoneBack1;
    public ModelRenderer stoneBack2;

    public ModelRockTroll()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.stoneBack1 = new ModelRenderer(this, 0, 77);
        this.stoneBack1.setRotationPoint(0.0F, -1F, 6.0F);
        this.stoneBack1.addBox(-10.0F, 0.0F, -2.0F, 20, 24, 4, 0.0F);
        this.legLeft = new ModelRenderer(this, 72, 0);
        this.legLeft.setRotationPoint(6.0F, 26.0F, 0.0F);
        this.legLeft.addBox(-5.0F, 0.0F, -5.0F, 10, 12, 10, 0.0F);
        this.head = new ModelRenderer(this, 0, 38);
        this.head.setRotationPoint(0.0F, 4.0F, -4.0F);
        this.head.addBox(-5.0F, -6.0F, -9.0F, 10, 10, 10, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, -14.0F, 0.0F);
        this.body.addBox(-12.0F, 0.0F, -6.0F, 24, 26, 12, 0.0F);
        this.stoneBack2 = new ModelRenderer(this, 0, 58);
        this.stoneBack2.setRotationPoint(0.0F, 3F, 2F);
        this.stoneBack2.addBox(-7.0F, 0.0F, 0.0F, 14, 14, 2, 0.0F);
        this.armLeft = new ModelRenderer(this, 40, 38);
        this.armLeft.setRotationPoint(12.0F, 2F, 0.0F);
        this.armLeft.addBox(0.0F, -1.0F, -5.5F, 8, 28, 11, 0.0F);
        this.armRight = new ModelRenderer(this, 78, 44);
        this.armRight.setRotationPoint(-12.0F, 2F, 0.0F);
        this.armRight.addBox(-8.0F, -1.0F, -5.5F, 8, 28, 11, 0.0F);
        this.legRight = new ModelRenderer(this, 72, 22);
        this.legRight.setRotationPoint(-6.0F, 26.0F, 0.0F);
        this.legRight.addBox(-5.0F, 0.0F, -5.0F, 10, 12, 10, 0.0F);

        this.body.addChild(armRight);
        this.body.addChild(armLeft);
        this.body.addChild(stoneBack1);
        this.stoneBack1.addChild(stoneBack2);
        this.body.addChild(legLeft);
        this.body.addChild(legRight);
        this.body.addChild(head);
    }

    @Override
    public void render(@Nonnull Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, @Nonnull Entity entityIn)
    {
        this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

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

        int defence = ((EntityRockTroll) entityIn).defenceTime;
        if(defence > 0)
        {
            this.armLeft.rotateAngleX = defence;
            this.armRight.rotateAngleX = defence;
        }
    }
}
