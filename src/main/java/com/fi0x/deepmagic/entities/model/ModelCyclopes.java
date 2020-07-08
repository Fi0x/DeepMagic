package com.fi0x.deepmagic.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelCyclopse - Fi0x
 * Created using Tabula 7.1.0
 */
public class ModelCyclopes extends ModelBase
{
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer legLeft;
    public ModelRenderer legRight;
    public ModelRenderer armLeft;
    public ModelRenderer armRight;

    public ModelCyclopes()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, -28.0F, 0.0F);
        this.body.addBox(-9.0F, 0.0F, -5.0F, 18, 28, 12, 0.0F);
        this.head = new ModelRenderer(this, 60, 0);
        this.head.setRotationPoint(0.0F, 1.0F, -4.0F);
        this.head.addBox(-5.0F, -4.0F, -6.0F, 10, 9, 8, 0.0F);
        this.armRight = new ModelRenderer(this, 84, 41);
        this.armRight.setRotationPoint(-8.0F, 1.5F, 0.0F);
        this.armRight.addBox(-9.0F, -1.0F, -4.0F, 8, 28, 8, 0.0F);
        this.armLeft = new ModelRenderer(this, 0, 40);
        this.armLeft.setRotationPoint(8.0F, 1.5F, 0.0F);
        this.armLeft.addBox(1.0F, -1.0F, -4.0F, 8, 28, 8, 0.0F);
        this.legLeft = new ModelRenderer(this, 88, 9);
        this.legLeft.setRotationPoint(4.5F, 28.0F, 0.0F);
        this.legLeft.addBox(-4.0F, 0.0F, -4.0F, 8, 24, 8, 0.0F);
        this.legRight = new ModelRenderer(this, 52, 32);
        this.legRight.setRotationPoint(-4.5F, 28.0F, 0.0F);
        this.legRight.addBox(-4.0F, 0.0F, -4.0F, 8, 24, 8, 0.0F);

        this.body.addChild(head);
        this.body.addChild(armLeft);
        this.body.addChild(armRight);
        this.body.addChild(legLeft);
        this.body.addChild(legRight);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
    }
}
