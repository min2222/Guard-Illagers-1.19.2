package com.min01.guardillagers.init;

import com.min01.guardillagers.GuardIllagers;
import com.min01.guardillagers.entity.GuardIllagerEntity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IllagerEntityRegistry 
{
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, GuardIllagers.MODID);

	public static final RegistryObject<EntityType<GuardIllagerEntity>> GUARD_ILLAGER = ENTITY_TYPES.register("guard_illager",
            () -> EntityType.Builder.of(GuardIllagerEntity::new, MobCategory.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).sized(0.6F, 1.95F).build(new ResourceLocation(GuardIllagers.MODID, "guard_illager").toString()));
}
