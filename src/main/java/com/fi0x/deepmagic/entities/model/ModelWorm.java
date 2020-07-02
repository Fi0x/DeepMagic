package com.fi0x.deepmagic.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelWorm - Fi0x
 * Created using Tabula 7.1.0
 */
public class ModelWorm extends ModelBase {
    public ModelRenderer body1;
    public ModelRenderer head;
    public ModelRenderer neck1;
    public ModelRenderer connector1;
    public ModelRenderer body2;
    public ModelRenderer connector2;
    public ModelRenderer body3;
    public ModelRenderer connector3;
    public ModelRenderer body4;

    public ModelWorm() {
        this.textureWidth = 32;
        this.textureHeight = 16;
        this.body2 = new ModelRenderer(this, 16, 0);
        this.body2.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.body2.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.body4 = new ModelRenderer(this, 0, 4);
        this.body4.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.body4.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.connector1 = new ModelRenderer(this, 14, 0);
        this.connector1.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.connector1.addBox(-0.5F, -0.5F, -0.25F, 1, 1, 1, 0.0F);
        this.connector3 = new ModelRenderer(this, 26, 0);
        this.connector3.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.connector3.addBox(-0.5F, -0.5F, -0.25F, 1, 1, 1, 0.0F);
        this.body1 = new ModelRenderer(this, 0, 0);
        this.body1.setRotationPoint(0.0F, 23.0F, -4.0F);
        this.body1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.neck1 = new ModelRenderer(this, 6, 0);
        this.neck1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.neck1.addBox(-0.5F, -0.5F, -0.75F, 1, 1, 1, 0.0F);
        this.head = new ModelRenderer(this, 8, 0);
        this.head.setRotationPoint(0.0F, 0.0F, -0.5F);
        this.head.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2, 0.0F);
        this.connector2 = new ModelRenderer(this, 22, 0);
        this.connector2.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.connector2.addBox(-0.5F, -0.5F, -0.25F, 1, 1, 1, 0.0F);
        this.body3 = new ModelRenderer(this, 22, 2);
        this.body3.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.body3.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);

        body1.addChild(neck1);
        body1.addChild(connector1);
        neck1.addChild(head);
        connector1.addChild(body2);
        body2.addChild(connector2);
        connector2.addChild(body3);
        body3.addChild(connector3);
        connector3.addChild(body4);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
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
}
