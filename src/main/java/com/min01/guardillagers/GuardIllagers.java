package com.min01.guardillagers;

import com.min01.guardillagers.event.EntityEventHandler;
import com.min01.guardillagers.init.IllagerEntityRegistry;
import com.min01.guardillagers.init.IllagerItems;
import com.min01.guardillagers.init.IllagerSoundsRegister;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GuardIllagers.MODID)
public class GuardIllagers 
{
    public static final String MODID = "guardillagers";
    public static final ResourceLocation LOOT_TABLE = new ResourceLocation("guardillagers", "entity/guard_illager");
    public static final IEventBus MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
    
	public GuardIllagers() 
	{
		IllagerItems.ITEMS.register(MOD_EVENT_BUS);
		IllagerSoundsRegister.SOUND_EVENTS.register(MOD_EVENT_BUS);
		IllagerEntityRegistry.ENTITY_TYPES.register(MOD_EVENT_BUS);
		MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
	}
}
