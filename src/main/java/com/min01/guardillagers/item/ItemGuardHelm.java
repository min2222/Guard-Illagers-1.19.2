package com.min01.guardillagers.item;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.min01.guardillagers.client.model.GuardHatModel;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ItemGuardHelm extends ArmorItem
{
	protected static final ResourceLocation HAT_TEX = new ResourceLocation("guardillagers", "textures/overlay/guard_perspective.png");
	
	public ItemGuardHelm(ArmorMaterial p_40386_, EquipmentSlot p_40387_, Properties p_40388_) 
	{
		super(p_40386_, p_40387_, p_40388_);
	}
	
	@Override
	public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) 
	{
		return "guardillagers:textures/models/armor/guard_hat.png";
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer)
	{
		consumer.accept(new IClientItemExtensions() 
		{
			@Override
			public void renderHelmetOverlay(ItemStack stack, Player player, int width, int height, float partialTick) 
			{
				RenderSystem.disableDepthTest();
				RenderSystem.depthMask(false);
				RenderSystem.defaultBlendFunc();
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
				RenderSystem.setShaderTexture(0, HAT_TEX);
				Tesselator tesselator = Tesselator.getInstance();
				BufferBuilder bufferbuilder = tesselator.getBuilder();
				bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
				bufferbuilder.vertex(0.0D, (double)height, -90.0D).uv(0.0F, 1.0F).endVertex();
				bufferbuilder.vertex((double)width, (double)height, -90.0D).uv(1.0F, 1.0F).endVertex();
				bufferbuilder.vertex((double)width, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
				bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
				tesselator.end();
				RenderSystem.depthMask(true);
				RenderSystem.enableDepthTest();
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			}
			
			@Override
			public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) 
			{
				return new GuardHatModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(GuardHatModel.LAYER_LOCATION), livingEntity);
			}
		});
	}
}
