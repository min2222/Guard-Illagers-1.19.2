package com.min01.guardillagers.client.model;

import com.min01.guardillagers.GuardIllagers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;

public class GuardHatModel<T extends LivingEntity> extends HumanoidModel<T>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(GuardIllagers.MODID, "guard_hat"), "main");
	
	private final ModelPart Hat1;
	
	private T entity;

	public GuardHatModel(ModelPart root, T entity) 
	{
		super(root);
		this.Hat1 = root.getChild("Hat1");
		this.entity = entity;
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Hat1 = partdefinition.addOrReplaceChild("Hat1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		Hat1.addOrReplaceChild("Hat2", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.14F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		Hat1.addOrReplaceChild("Hat3", CubeListBuilder.create().texOffs(-16, 16).addBox(-8.0F, -3.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
        if (this.entity.isCrouching()) 
        {
        	poseStack.translate(0.0f, 0.2f, 0.0f);
        }
        if (this.entity instanceof ArmorStand) 
        {
            ArmorStand entityarmorstand = (ArmorStand) this.entity;
            this.Hat1.xRot = 0.017453292f * entityarmorstand.getHeadPose().getX();
            this.Hat1.yRot = 0.017453292f * entityarmorstand.getHeadPose().getY();
            this.Hat1.zRot = 0.017453292f * entityarmorstand.getHeadPose().getZ();
        }
		this.setAngle(this.Hat1, this.head);
		this.Hat1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
    protected void setAngle(ModelPart modelRenderer, ModelPart modelRenderer2)
    {
        modelRenderer.x = modelRenderer2.x;
        modelRenderer.y = modelRenderer2.y;
        modelRenderer.z = modelRenderer2.z;
        modelRenderer.xRot = modelRenderer2.xRot;
        modelRenderer.yRot = modelRenderer2.yRot;
        modelRenderer.zRot = modelRenderer2.zRot;
    }
}
