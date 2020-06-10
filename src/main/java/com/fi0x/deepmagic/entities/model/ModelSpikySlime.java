package com.fi0x.deepmagic.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;

/**
 * ModelSpikySlime - Either Mojang or a mod author
 * Created using Tabula 7.1.0
 */
public class ModelSpikySlime extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer mouth;
    public ModelRenderer eyeLeft;
    public ModelRenderer eyeRight;
    public ModelRenderer spike1;
    public ModelRenderer spike2;
    public ModelRenderer spike3;
    public ModelRenderer spike4;
    public ModelRenderer spike5;
    public ModelRenderer spike6;
    public ModelRenderer spike7;
    public ModelRenderer spike8;
    public ModelRenderer spike9;
    public ModelRenderer spike10;
    public ModelRenderer spike11;
    public ModelRenderer spike12;
    public ModelRenderer spike13;
    public ModelRenderer spike14;
    public ModelRenderer spike15;
    public ModelRenderer spike16;
    public ModelRenderer spike17;
    public ModelRenderer spike18;

    public ModelSpikySlime() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.spike7 = new ModelRenderer(this, 26, 0);
        this.spike7.setRotationPoint(3.0F, 22.0F, -1.5F);
        this.spike7.addBox(0.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(spike7, 0.36425021489121656F, 0.40980330836826856F, 0.40980330836826856F);
        this.spike1 = new ModelRenderer(this, 26, 0);
        this.spike1.setRotationPoint(-2.0F, 17.0F, -1.4F);
        this.spike1.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(spike1, 0.7740535232594852F, 0.9105382707654417F, 0.0F);
        this.spike15 = new ModelRenderer(this, 26, 0);
        this.spike15.setRotationPoint(-3.0F, 20.0F, 0.0F);
        this.spike15.addBox(0.0F, -0.5F, -0.5F, -2, 1, 1, 0.0F);
        this.setRotateAngle(spike15, 0.0F, 0.0F, -0.27314402793711257F);
        this.spike18 = new ModelRenderer(this, 26, 0);
        this.spike18.setRotationPoint(-3.0F, 21.6F, 2.0F);
        this.spike18.addBox(0.0F, -0.5F, -0.5F, -2, 1, 1, 0.0F);
        this.setRotateAngle(spike18, 0.0F, 0.4553564018453205F, -0.40980330836826856F);
        this.spike4 = new ModelRenderer(this, 26, 0);
        this.spike4.setRotationPoint(2.1F, 17.0F, -1.9F);
        this.spike4.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(spike4, 0.31869712141416456F, -0.7285004297824331F, 0.0F);
        this.spike2 = new ModelRenderer(this, 26, 0);
        this.spike2.setRotationPoint(-1.4F, 17.0F, 1.5F);
        this.spike2.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(spike2, 0.40980330836826856F, 2.0943951023931953F, 0.0F);
        this.spike9 = new ModelRenderer(this, 26, 0);
        this.spike9.setRotationPoint(1.4F, 21.5F, 3.0F);
        this.spike9.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(spike9, -0.31869712141416456F, 0.40980330836826856F, 0.0F);
        this.spike13 = new ModelRenderer(this, 26, 0);
        this.spike13.setRotationPoint(-1.2F, 18.0F, 3.0F);
        this.spike13.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(spike13, 0.5462880558742251F, -0.31869712141416456F, 0.0F);
        this.spike5 = new ModelRenderer(this, 26, 0);
        this.spike5.setRotationPoint(3.0F, 18.3F, 1.6F);
        this.spike5.addBox(0.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(spike5, 0.7285004297824331F, -0.5462880558742251F, -0.6829473363053812F);
        this.spike8 = new ModelRenderer(this, 26, 0);
        this.spike8.setRotationPoint(3.0F, 19.8F, -0.9F);
        this.spike8.addBox(0.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(spike8, 0.0F, 0.5009094953223726F, -0.5918411493512771F);
        this.spike11 = new ModelRenderer(this, 26, 0);
        this.spike11.setRotationPoint(0.0F, 19.8F, 3.0F);
        this.spike11.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(spike11, 0.136659280431156F, 0.22759093446006054F, 0.0F);
        this.spike12 = new ModelRenderer(this, 26, 0);
        this.spike12.setRotationPoint(2.0F, 18.0F, 3.0F);
        this.spike12.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(spike12, 0.5462880558742251F, 0.4553564018453205F, 0.0F);
        this.spike6 = new ModelRenderer(this, 26, 0);
        this.spike6.setRotationPoint(3.0F, 21.2F, 1.3F);
        this.spike6.addBox(0.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(spike6, -0.5918411493512771F, -0.40980330836826856F, 0.40980330836826856F);
        this.eyeLeft = new ModelRenderer(this, 18, 0);
        this.eyeLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.eyeLeft.addBox(1.25F, 18.0F, -3.5F, 2, 2, 2, 0.0F);
        this.spike10 = new ModelRenderer(this, 26, 0);
        this.spike10.setRotationPoint(-2.1F, 20.7F, 3.0F);
        this.spike10.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(spike10, -0.5009094953223726F, -0.4553564018453205F, 0.0F);
        this.spike14 = new ModelRenderer(this, 26, 0);
        this.spike14.setRotationPoint(-3.0F, 18.0F, -0.9F);
        this.spike14.addBox(0.0F, -0.5F, -0.5F, -2, 1, 1, 0.0F);
        this.setRotateAngle(spike14, 0.27314402793711257F, -0.40980330836826856F, 0.4553564018453205F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-3.0F, 17.0F, -3.0F, 6, 6, 6, 0.0F);
        this.eyeRight = new ModelRenderer(this, 22, 10);
        this.eyeRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.eyeRight.addBox(-3.25F, 18.0F, -3.5F, 2, 2, 2, 0.0F);
        this.spike3 = new ModelRenderer(this, 26, 0);
        this.spike3.setRotationPoint(1.4F, 17.0F, 0.8F);
        this.spike3.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(spike3, -0.40980330836826856F, 0.22759093446006054F, 0.0F);
        this.spike17 = new ModelRenderer(this, 26, 0);
        this.spike17.setRotationPoint(-3.0F, 22.0F, -1.0F);
        this.spike17.addBox(0.0F, -0.5F, -0.5F, -2, 1, 1, 0.0F);
        this.setRotateAngle(spike17, 0.0F, -0.4553564018453205F, -0.4553564018453205F);
        this.mouth = new ModelRenderer(this, 0, 0);
        this.mouth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.mouth.addBox(0.0F, 21.0F, -3.5F, 1, 1, 1, 0.0F);
        this.spike16 = new ModelRenderer(this, 26, 0);
        this.spike16.setRotationPoint(-3.0F, 19.1F, 1.4F);
        this.spike16.addBox(0.0F, -0.5F, -0.5F, -2, 1, 1, 0.0F);
        this.setRotateAngle(spike16, 0.0F, 0.36425021489121656F, 0.5009094953223726F);
    }

    @Override
    public void render(@Nonnull Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.spike7.render(f5);
        this.spike1.render(f5);
        this.spike15.render(f5);
        this.spike18.render(f5);
        this.spike4.render(f5);
        this.spike2.render(f5);
        this.spike9.render(f5);
        this.spike13.render(f5);
        this.spike5.render(f5);
        this.spike8.render(f5);
        this.spike11.render(f5);
        this.spike12.render(f5);
        this.spike6.render(f5);
        this.eyeLeft.render(f5);
        this.spike10.render(f5);
        this.spike14.render(f5);
        this.body.render(f5);
        this.eyeRight.render(f5);
        this.spike3.render(f5);
        this.spike17.render(f5);
        this.mouth.render(f5);
        this.spike16.render(f5);
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
