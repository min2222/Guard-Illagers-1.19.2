package com.min01.guardillagers.event;

import com.min01.guardillagers.GuardIllagers;
import com.min01.guardillagers.entity.GuardIllagerEntity;
import com.min01.guardillagers.init.IllagerEntityRegistry;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GuardIllagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IllagerAttributeEvent
{
    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) 
    {
    	event.put(IllagerEntityRegistry.GUARD_ILLAGER.get(), GuardIllagerEntity.createAttributes().build());
    }
}
