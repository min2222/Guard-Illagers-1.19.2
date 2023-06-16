package com.min01.guardillagers.client;

import com.min01.guardillagers.GuardIllagers;
import com.min01.guardillagers.client.model.GuardHatModel;
import com.min01.guardillagers.client.model.GuardIllagerModel;
import com.min01.guardillagers.client.render.GuardIllagerRender;
import com.min01.guardillagers.init.IllagerEntityRegistry;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GuardIllagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class IllagerEntityRender 
{
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
    	event.registerLayerDefinition(GuardHatModel.LAYER_LOCATION, GuardHatModel::createBodyLayer);
    	event.registerLayerDefinition(GuardIllagerModel.LAYER_LOCATION, GuardIllagerModel::createBodyLayer);
    }
    
    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
    	event.registerEntityRenderer(IllagerEntityRegistry.GUARD_ILLAGER.get(), GuardIllagerRender::new);
    }
}
