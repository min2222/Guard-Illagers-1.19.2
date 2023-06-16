package com.min01.guardillagers.init;

import com.min01.guardillagers.GuardIllagers;
import com.min01.guardillagers.item.ItemGuardHelm;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IllagerItems 
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GuardIllagers.MODID);
	public static final RegistryObject<Item> GUARD_HELM = ITEMS.register("guard_helm", () -> new ItemGuardHelm(IllagersArmorMaterial.GUARD_HELM, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
	public static final RegistryObject<Item> GUARD_ILLAGER_EGG = ITEMS.register("guard_illager_egg", () -> new ForgeSpawnEggItem(() -> IllagerEntityRegistry.GUARD_ILLAGER.get(), 9804699, 8887451, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}
