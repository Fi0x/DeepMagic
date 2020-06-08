package com.fi0x.deepmagic.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelCow - Either Mojang or a mod author
 * Created using Tabula 7.1.0
 */
public class ModelInsanityCow extends ModelBase {
    public ModelRenderer Body;
    public ModelRenderer field_78148_b1;
    public ModelRenderer Leg_Back_Left;
    public ModelRenderer Leg_Front_Left;
    public ModelRenderer Leg_Back_Right;
    public ModelRenderer Leg_Front_Right;
    public ModelRenderer Head;
    public ModelRenderer Right_Ear;
    public ModelRenderer Left_Ear;
    public ModelRenderer Head2;
    public ModelRenderer Right_Ear2;
    public ModelRenderer Left_Ear2;
    public ModelRenderer Wing_Right;
    public ModelRenderer Wing_Left;
    public ModelRenderer Tail1;
    public ModelRenderer Tail2;
    public ModelRenderer Tail12;
    public ModelRenderer Tail22;

    public ModelInsanityCow() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Tail22 = new ModelRenderer(this, 4, 15);
        this.Tail22.setRotationPoint(-2.0F, 8.0F, 15.5F);
        this.Tail22.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.setRotateAngle(Tail22, 1.9198621771937625F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(-5.0F, 4.0F, -8.0F);
        this.Head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
        this.Wing_Left = new ModelRenderer(this, 24, 13);
        this.Wing_Left.setRotationPoint(6.0F, 2.3F, 0.0F);
        this.Wing_Left.addBox(0.0F, 0.0F, 0.0F, 1, 4, 6, 0.0F);
        this.setRotateAngle(Wing_Left, 0.0F, 0.0F, -0.7853981633974483F);
        this.Leg_Front_Left = new ModelRenderer(this, 0, 16);
        this.Leg_Front_Left.setRotationPoint(4.0F, 12.0F, -6.0F);
        this.Leg_Front_Left.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Tail2 = new ModelRenderer(this, 4, 15);
        this.Tail2.setRotationPoint(2.0F, 8.0F, 15.5F);
        this.Tail2.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.setRotateAngle(Tail2, 1.9198621771937625F, 0.0F, 0.0F);
        this.Tail12 = new ModelRenderer(this, 0, 15);
        this.Tail12.setRotationPoint(-2.0F, 3.0F, 9.5F);
        this.Tail12.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.setRotateAngle(Tail12, 0.8726646259971648F, 0.0F, 0.0F);
        this.Right_Ear = new ModelRenderer(this, 22, 0);
        this.Right_Ear.setRotationPoint(-5.0F, 4.0F, -8.0F);
        this.Right_Ear.addBox(-5.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.Leg_Front_Right = new ModelRenderer(this, 0, 16);
        this.Leg_Front_Right.setRotationPoint(-4.0F, 12.0F, -6.0F);
        this.Leg_Front_Right.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Wing_Right = new ModelRenderer(this, 24, 13);
        this.Wing_Right.setRotationPoint(-6.5F, 1.7F, 0.0F);
        this.Wing_Right.addBox(0.0F, 0.0F, 0.0F, 1, 4, 6, 0.0F);
        this.setRotateAngle(Wing_Right, 0.0F, 0.0F, 0.7853981633974483F);
        this.Leg_Back_Right = new ModelRenderer(this, 0, 16);
        this.Leg_Back_Right.setRotationPoint(-4.0F, 12.0F, 7.0F);
        this.Leg_Back_Right.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Head2 = new ModelRenderer(this, 0, 0);
        this.Head2.setRotationPoint(1.0F, 0.1F, -14.0F);
        this.Head2.addBox(0.0F, 0.0F, 0.0F, 8, 8, 6, 0.0F);
        this.Leg_Back_Left = new ModelRenderer(this, 0, 16);
        this.Leg_Back_Left.setRotationPoint(4.0F, 12.0F, 7.0F);
        this.Leg_Back_Left.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Tail1 = new ModelRenderer(this, 0, 15);
        this.Tail1.setRotationPoint(2.0F, 3.0F, 9.5F);
        this.Tail1.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.setRotateAngle(Tail1, 0.8726646259971648F, 0.0F, 0.0F);
        this.field_78148_b1 = new ModelRenderer(this, 52, 0);
        this.field_78148_b1.setRotationPoint(0.0F, 5.0F, 2.0F);
        this.field_78148_b1.addBox(-2.0F, 2.0F, -8.0F, 4, 6, 1, 0.0F);
        this.setRotateAngle(field_78148_b1, 1.5707963705062866F, 0.0F, 0.0F);
        this.Body = new ModelRenderer(this, 18, 4);
        this.Body.setRotationPoint(0.0F, 5.0F, 2.0F);
        this.Body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
        this.setRotateAngle(Body, 1.5707963267948966F, 0.0F, 0.0F);
        this.Left_Ear = new ModelRenderer(this, 22, 0);
        this.Left_Ear.setRotationPoint(-5.0F, 4.0F, -8.0F);
        this.Left_Ear.addBox(4.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.Left_Ear2 = new ModelRenderer(this, 22, 0);
        this.Left_Ear2.setRotationPoint(9.0F, -1.0F, -12.0F);
        this.Left_Ear2.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.Right_Ear2 = new ModelRenderer(this, 22, 0);
        this.Right_Ear2.setRotationPoint(0.0F, -1.0F, -12.0F);
        this.Right_Ear2.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Tail22.render(f5);
        this.Head.render(f5);
        this.Wing_Left.render(f5);
        this.Leg_Front_Left.render(f5);
        this.Tail2.render(f5);
        this.Tail12.render(f5);
        this.Right_Ear.render(f5);
        this.Leg_Front_Right.render(f5);
        this.Wing_Right.render(f5);
        this.Leg_Back_Right.render(f5);
        this.Head2.render(f5);
        this.Leg_Back_Left.render(f5);
        this.Tail1.render(f5);
        this.field_78148_b1.render(f5);
        this.Body.render(f5);
        this.Left_Ear.render(f5);
        this.Left_Ear2.render(f5);
        this.Right_Ear2.render(f5);
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
    	this.Leg_Front_Left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F *limbSwingAmount;
    	this.Leg_Back_Left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F *limbSwingAmount;
    	this.Leg_Front_Right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F *limbSwingAmount;
    	this.Leg_Back_Right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F *limbSwingAmount;
    	
    	this.Head.rotateAngleY = netHeadYaw * 0.17453292F;
    	this.Head2.rotateAngleY = netHeadYaw * 0.17453292F;
    	
    	this.Head.rotateAngleX = headPitch * 0.17453292F;
    	this.Head2.rotateAngleX = headPitch * 0.17453292F;
    }
}
