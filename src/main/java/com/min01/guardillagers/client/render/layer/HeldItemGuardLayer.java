package com.min01.guardillagers.client.render.layer;

import com.min01.guardillagers.client.model.GuardIllagerModel;
import com.min01.guardillagers.entity.GuardIllagerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;

public class HeldItemGuardLayer<T extends GuardIllagerEntity> extends RenderLayer<T, GuardIllagerModel<T>> 
{
	private final GuardIllagerModel<T> guardModel;
	
	public HeldItemGuardLayer(RenderLayerParent<T, GuardIllagerModel<T>> p_117346_)
	{
		super(p_117346_);
		this.guardModel = p_117346_.getModel();
	}
	
	@Override
	public void render(PoseStack posestack, MultiBufferSource p_117350_, int p_117351_, T entitylivingbaseIn, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_)
	{
        ItemStack itemstack = entitylivingbaseIn.getOffhandItem();
        //AbstractIllager.IllagerArmPose abstractillager$illagerarmpose = entitylivingbaseIn.getArmPose();
        if (!(itemstack.getItem() instanceof ShieldItem) && !itemstack.isEmpty())
        {
            posestack.pushPose();
            if (this.guardModel.young)
            {
                posestack.translate(0.0f, 0.625f, 0.0f);
                posestack.mulPose(Vector3f.XN.rotationDegrees(-20.0f));
                //float f = 0.5f;
                posestack.scale(0.5f, 0.5f, 0.5f);
            }
            this.guardModel.crossHand().translateAndRotate(posestack);
            posestack.translate(-0.0625f, 0.53125f, 0.21875f);
            Item item = itemstack.getItem();
            Minecraft minecraft = Minecraft.getInstance();
            if (Block.byItem(item).defaultBlockState().getRenderShape() == RenderShape.ENTITYBLOCK_ANIMATED) 
            {
                posestack.translate(0.0f, -0.2875f, -0.46f);
                posestack.mulPose(Vector3f.XP.rotationDegrees(30.0f));
                posestack.mulPose(Vector3f.YP.rotationDegrees(-5.0f));
                //float f2 = 0.375f;
                posestack.scale(0.375f, -0.375f, 0.375f);
            }
            else if (item == Items.BOW)
            {
                posestack.translate(0.0f, -0.2875f, -0.46f);
                posestack.mulPose(Vector3f.YP.rotationDegrees(-45.0f));
                //float f3 = 0.625f;
                posestack.scale(0.625f, -0.625f, 0.625f);
                posestack.mulPose(Vector3f.XP.rotationDegrees(-100.0f));
                posestack.mulPose(Vector3f.YP.rotationDegrees(-20.0f));
            }
            else 
            {
                posestack.translate(0.0f, -0.2875f, -0.46f);
                //float f4 = 0.875f;
                posestack.scale(0.875f, 0.875f, 0.95f);
                posestack.mulPose(Vector3f.YP.rotationDegrees(-30.0f));
                posestack.mulPose(Vector3f.XP.rotationDegrees(-60.0f));
            }
            posestack.mulPose(Vector3f.XP.rotationDegrees(-15.0f));
            posestack.mulPose(Vector3f.ZP.rotationDegrees(40.0f));
            minecraft.getItemRenderer().renderStatic(entitylivingbaseIn, itemstack, TransformType.THIRD_PERSON_RIGHT_HAND, false, posestack, p_117350_, entitylivingbaseIn.level, p_117351_, LivingEntityRenderer.getOverlayCoords(entitylivingbaseIn, 0.0F), entitylivingbaseIn.getId());
            posestack.popPose();
        }
	}
}
