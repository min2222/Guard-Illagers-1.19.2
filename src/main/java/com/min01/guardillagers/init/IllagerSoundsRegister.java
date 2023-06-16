package com.min01.guardillagers.init;

import com.min01.guardillagers.GuardIllagers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IllagerSoundsRegister 
{
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, GuardIllagers.MODID);
	
	public static final RegistryObject<SoundEvent> GUARDILLAGER_AMBIENT = create("mob.guardillager.ambient");
	public static final RegistryObject<SoundEvent> GUARDILLAGER_ANGRY = create("mob.guardillager.angry");
	public static final RegistryObject<SoundEvent> GUARDILLAGER_HURT = create("mob.guardillager.hurt");
	public static final RegistryObject<SoundEvent> GUARDILLAGER_DIE = create("mob.guardillager.die");
	public static final RegistryObject<SoundEvent> GUARDILLAGER_STEP = create("mob.guardillager.step");
	
	private static RegistryObject<SoundEvent> create(String name) 
	{
		return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(GuardIllagers.MODID, name)));
    }
}
