package com.min01.guardillagers.client.model;

import com.min01.guardillagers.GuardIllagers;
import com.min01.guardillagers.entity.GuardIllagerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.AbstractIllager.IllagerArmPose;

public class GuardIllagerModel<T extends GuardIllagerEntity> extends EntityModel<T> implements ArmedModel, HeadedModel
{
	public static ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(GuardIllagers.MODID, "guard_illager"), "main");
	
    public ModelPart Head;
    public ModelPart Nose;
    public ModelPart HatLayer;
    public ModelPart HatFlap;
    public ModelPart Body;
    public ModelPart ChestPlate;
    public ModelPart RightLeg;
    public ModelPart LeftLeg;
    public ModelPart RightOpenArm;
    public ModelPart LeftOpenArm;
    public ModelPart MiddleClosedArm;
    public ModelPart Cape;
    public ModelPart RightClosedArm;
    public ModelPart LeftClosedArm;
    
    public AbstractIllager entity;
    
	public GuardIllagerModel(ModelPart root) 
	{
		this.MiddleClosedArm = root.getChild("MiddleClosedArm");
		this.LeftLeg = root.getChild("LeftLeg");
		this.RightClosedArm = this.MiddleClosedArm.getChild("RightClosedArm");
		this.Head = root.getChild("Head");
		this.Nose = this.Head.getChild("Nose");
		this.LeftOpenArm = root.getChild("LeftOpenArm");
		this.RightLeg = root.getChild("RightLeg");
		this.ChestPlate = root.getChild("ChestPlate");
		this.LeftClosedArm = this.MiddleClosedArm.getChild("LeftClosedArm");
		this.HatFlap = this.Head.getChild("HatFlap");
		this.RightOpenArm = root.getChild("RightOpenArm");
		this.Cape = root.getChild("Cape");
		this.HatLayer = this.Head.getChild("HatLayer");
		this.ChestPlate = root.getChild("ChestPlate");
		this.Body = root.getChild("Body");
	}
    
	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition MiddleClosedArm = partdefinition.addOrReplaceChild("MiddleClosedArm", CubeListBuilder.create().texOffs(0, 56).addBox(-4.0f, 2.0f, -2.0f, 8, 4, 4), PartPose.offsetAndRotation(0.0f, 3.0f, -1.0f, -0.7853982f, 0.0f, 0.0f));
		partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4).mirror(), PartPose.offset(2.0f, 12.0f, 0.0f));
		MiddleClosedArm.addOrReplaceChild("RightClosedArm", CubeListBuilder.create().texOffs(48, 36).addBox(-8.0f, -2.0f, -2.0f, 4, 8, 4), PartPose.offset(0.0f, 0.0f, 0.0f));
		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -10.0f, -4.0f, 8, 10, 8), PartPose.offset(0.0f, 0.0f, 0.0f));
		Head.addOrReplaceChild("Nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0f, 0.0f, -2.0f, 2, 4, 2), PartPose.offset(0.0f, -3.0f, -4.0f));
		partdefinition.addOrReplaceChild("LeftOpenArm", CubeListBuilder.create().texOffs(44, 18).addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4).mirror(), PartPose.offset(5.0f, 2.0f, 0.0f));
		partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4), PartPose.offset(-2.0f, 12.0f, 0.0f));
		partdefinition.addOrReplaceChild("ChestPlate", CubeListBuilder.create().texOffs(0, 36).addBox(-4.0f, 0.0f, -3.0f, 8, 12, 6), PartPose.offset(0.0f, 0.0f, 0.0f));
		MiddleClosedArm.addOrReplaceChild("LeftClosedArm", CubeListBuilder.create().texOffs(48, 36).addBox(4.0f, -2.0f, -2.0f, 4, 8, 4).mirror(), PartPose.offset(0.0f, 0.0f, 0.0f));
		Head.addOrReplaceChild("HatFlap", CubeListBuilder.create().texOffs(16, 48).addBox(-8.0f, -4.9f, -8.0f, 16, 0, 16), PartPose.offset(0.0f, 0.0f, 0.0f));
		partdefinition.addOrReplaceChild("RightOpenArm", CubeListBuilder.create().texOffs(44, 18).addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4), PartPose.offset(-5.0f, 2.0f, 0.0f));
		partdefinition.addOrReplaceChild("Cape", CubeListBuilder.create().texOffs(0, 64).addBox(-4.0f, 0.0f, -1.0f, 8, 14, 1), PartPose.offsetAndRotation(0.0f, 0.0f, 3.6f, 0.17453292f, 0.0f, 0.0f));
		Head.addOrReplaceChild("HatLayer", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0f, -9.9f, -4.0f, 8, 10, 8, new CubeDeformation(0.5f)), PartPose.offset(0.0f, 0.0f, 0.0f));
		partdefinition.addOrReplaceChild("ChestPlate", CubeListBuilder.create().texOffs(0, 36).addBox(-4.0f, 0.0f, -3.0f, 8, 14, 6, new CubeDeformation(0.5f)), PartPose.offset(0.0f, 0.0f, 0.0f));
		partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(16, 18).addBox(-4.0f, 0.0f, -3.0f, 8, 12, 6), PartPose.offset(0.0f, 0.0f, 0.0f));
		return LayerDefinition.create(meshdefinition, 64, 128);
	}
	
	@Override
	public void setupAnim(T p_102618_, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.entity = p_102618_;
        this.Head.yRot = (float)Math.toRadians(netHeadYaw);
        this.Head.xRot = (float)Math.toRadians(headPitch);
        this.MiddleClosedArm.y = 3.0f;
        this.MiddleClosedArm.z = -1.0f;
        this.MiddleClosedArm.xRot = -0.75f;
        this.RightLeg.xRot = Mth.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount * 0.5f;
        this.LeftLeg.xRot = Mth.cos(limbSwing * 0.6662f + 3.1415927f) * 1.4f * limbSwingAmount * 0.5f;
        this.RightLeg.yRot = 0.0f;
        this.LeftLeg.yRot = 0.0f;
        GuardIllagerEntity guard = p_102618_;
        IllagerArmPose armPose = guard.getArmPose();
        if (armPose == IllagerArmPose.ATTACKING)
        {
            float f = Mth.sin(this.attackTime * 3.1415927f);
            float f2 = Mth.sin((1.0f - (1.0f - this.attackTime) * (1.0f - this.attackTime)) * 3.1415927f);
            this.RightOpenArm.zRot = 0.0f;
            this.LeftOpenArm.zRot = 0.0f;
            this.RightOpenArm.yRot = 0.15707964f;
            this.LeftOpenArm.yRot = -0.15707964f;
            if (guard.getMainArm() == HumanoidArm.RIGHT)
            {
                if (guard.isDrinkingPotion()) 
                {
                    this.RightOpenArm.xRot = -1.8849558f + Mth.cos(ageInTicks * 0.09f) * 0.15f;
                    this.LeftOpenArm.xRot = -1.0f;
                    this.LeftOpenArm.zRot = -0.6f;
                    ModelPart rightOpenArm = this.RightOpenArm;
                    rightOpenArm.xRot += f * 2.2f - f2 * 0.4f;
                    ModelPart leftOpenArm = this.LeftOpenArm;
                    leftOpenArm.xRot += f * 1.2f - f2 * 0.4f;
                }
                else 
                {
                    this.RightOpenArm.xRot = -1.8849558f + Mth.cos(ageInTicks * 0.09f) * 0.15f;
                    this.LeftOpenArm.xRot = -0.0f + Mth.cos(ageInTicks * 0.19f) * 0.5f;
                    ModelPart rightOpenArm2 = this.RightOpenArm;
                    rightOpenArm2.xRot += f * 2.2f - f2 * 0.4f;
                    ModelPart leftOpenArm2 = this.LeftOpenArm;
                    leftOpenArm2.xRot += f * 1.2f - f2 * 0.4f;
                }
            }
            else 
            {
                if (guard.isDrinkingPotion()) 
                {
                    this.RightOpenArm.zRot = 0.6f;
                    this.RightOpenArm.xRot = 1.0f;
                    this.LeftOpenArm.xRot = -1.8849558f + Mth.cos(ageInTicks * 0.09f) * 0.15f;
                    ModelPart rightOpenArm3 = this.RightOpenArm;
                    rightOpenArm3.xRot += f * 1.2f - f2 * 0.4f;
                    ModelPart leftOpenArm3 = this.LeftOpenArm;
                    leftOpenArm3.xRot += f * 2.2f - f2 * 0.4f;
                }
                this.RightOpenArm.xRot = -0.0f + Mth.cos(ageInTicks * 0.19f) * 0.5f;
                this.LeftOpenArm.xRot = -1.8849558f + Mth.cos(ageInTicks * 0.09f) * 0.15f;
                ModelPart rightOpenArm4 = this.RightOpenArm;
                rightOpenArm4.xRot += f * 1.2f - f2 * 0.4f;
                ModelPart leftOpenArm4 = this.LeftOpenArm;
                leftOpenArm4.xRot += f * 2.2f - f2 * 0.4f;
            }
            ModelPart rightOpenArm5 = this.RightOpenArm;
            rightOpenArm5.zRot += Mth.cos(ageInTicks * 0.09f) * 0.05f + 0.05f;
            ModelPart leftOpenArm5 = this.LeftOpenArm;
            leftOpenArm5.zRot -= Mth.cos(ageInTicks * 0.09f) * 0.05f + 0.05f;
            ModelPart rightOpenArm6 = this.RightOpenArm;
            rightOpenArm6.xRot += Mth.sin(ageInTicks * 0.067f) * 0.05f;
            ModelPart leftOpenArm6 = this.LeftOpenArm;
            leftOpenArm6.xRot -= Mth.sin(ageInTicks * 0.067f) * 0.05f;
        }
        else if (armPose == IllagerArmPose.BOW_AND_ARROW) 
        {
            this.RightOpenArm.yRot = -0.1f + this.Head.yRot;
            this.RightOpenArm.xRot = -1.5707964f + this.Head.xRot;
            this.LeftOpenArm.xRot = -0.9424779f + this.Head.xRot;
            this.LeftOpenArm.yRot = this.Head.yRot - 0.4f;
            this.LeftOpenArm.zRot = 1.5707964f;
        }
        float partialTicks = Minecraft.getInstance().getPartialTick();
        double capeX = guard.prevCapeX + (guard.capeX - guard.prevCapeX) * partialTicks;
        double capeY = guard.prevCapeY + (guard.capeY - guard.prevCapeY) * partialTicks;
        double capeZ = guard.prevCapeZ + (guard.capeZ - guard.prevCapeZ) * partialTicks;
        double guardX = guard.xo + (guard.getX() - guard.xo) * partialTicks;
        double guardY = guard.yo + (guard.getY() - guard.yo) * partialTicks;
        double guardZ = guard.zo + (guard.getZ() - guard.zo) * partialTicks;
        double deltaX = capeX - guardX;
        double deltaY = guardY - capeY;
        double deltaZ = capeZ - guardZ;
        deltaY = Math.sqrt(deltaY * deltaY);
        double deltaXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        this.Cape.xRot = (float)(1.5707963267948966 - Mth.atan2(deltaY, deltaXZ));
	}
	
	@Override
	public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, float p_103115_, float p_103116_, float p_103117_, float p_103118_)
	{
        this.Body.render(p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
        this.Head.render(p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
        this.LeftLeg.render(p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
        this.ChestPlate.render(p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
        this.RightLeg.render(p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
        this.Cape.render(p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
        AbstractIllager abstractillager = this.entity;
        if (abstractillager.getArmPose() == AbstractIllager.IllagerArmPose.CROSSED)
        {
            this.MiddleClosedArm.render(p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
        }
        else 
        {
            this.RightOpenArm.render(p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
            this.LeftOpenArm.render(p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
        }
	}
	
	@Override
	public ModelPart getHead() 
	{
		return this.Head;
	}
	
    public ModelPart getArm(HumanoidArm handSide) 
    {
        return (handSide == HumanoidArm.LEFT) ? this.LeftOpenArm : this.RightOpenArm;
    }
	
	@Override
	public void translateToHand(HumanoidArm p_102108_, PoseStack p_102109_) 
	{
		this.getArm(p_102108_).translateAndRotate(p_102109_);
	}
	
    public ModelPart crossHand()
    {
        return this.MiddleClosedArm;
    }
}
