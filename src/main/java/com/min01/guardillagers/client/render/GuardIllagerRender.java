package com.min01.guardillagers.client.render;

import com.min01.guardillagers.client.model.GuardIllagerModel;
import com.min01.guardillagers.client.render.layer.HeldItemGuardLayer;
import com.min01.guardillagers.entity.GuardIllagerEntity;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class GuardIllagerRender<T extends GuardIllagerEntity> extends MobRenderer<T, GuardIllagerModel<T>>
{
	private static final ResourceLocation ILLAGER_TEXTURE = new ResourceLocation("guardillagers", "textures/entity/illager/guardillager.png");
	
	public GuardIllagerRender(Context p_174304_)
	{
		super(p_174304_, new GuardIllagerModel<>(p_174304_.bakeLayer(GuardIllagerModel.LAYER_LOCATION)), 0.5f);
		this.addLayer(new CustomHeadLayer<>(this, p_174304_.getModelSet(), p_174304_.getItemInHandRenderer()));
		this.addLayer(new ItemInHandLayer<T, GuardIllagerModel<T>>(this, p_174304_.getItemInHandRenderer())
		{
			@Override
			public void render(PoseStack p_117204_, MultiBufferSource p_117205_, int p_117206_, T p_117207_, float p_117208_, float p_117209_, float p_117210_, float p_117211_, float p_117212_, float p_117213_) 
			{
				if(p_117207_.isAggressive())
				{
					super.render(p_117204_, p_117205_, p_117206_, p_117207_, p_117208_, p_117209_, p_117210_, p_117211_, p_117212_, p_117213_);
				}
			}
		});
		this.addLayer(new HeldItemGuardLayer<T>(this)
		{
			@Override
			public void render(PoseStack p_117204_, MultiBufferSource p_117205_, int p_117206_, T p_117207_, float p_117208_, float p_117209_, float p_117210_, float p_117211_, float p_117212_, float p_117213_) 
			{
				if(!p_117207_.isAggressive())
				{
					super.render(p_117204_, p_117205_, p_117206_, p_117207_, p_117208_, p_117209_, p_117210_, p_117211_, p_117212_, p_117213_);
				}
			}
		});
	}
	
	@Override
	public ResourceLocation getTextureLocation(T p_114482_) 
	{
		return ILLAGER_TEXTURE;
	}
	
	@Override
	protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) 
	{
		p_115315_.scale(0.9375f, 0.9375f, 0.9375f);
	}
}
