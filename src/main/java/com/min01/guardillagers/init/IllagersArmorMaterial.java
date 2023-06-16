package com.min01.guardillagers.init;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class IllagersArmorMaterial 
{
    private static final int[] DURABILITY = new int[] { 13, 15, 16, 11 };
    public static final ArmorMaterial GUARD_HELM = new Builder().withName("guardillagers:guard_hat").withDurabilityFactor(15).withDamageReductionAmounts(new int[] { 2, 3, 2, 2 }).withEnchantability(12).withRepairMaterial(Ingredient.of(Items.IRON_INGOT)).build();
    
    private static class Builder
    {
        private String name;
        private int durabilityFactor;
        private int[] damageReductionAmounts;
        private int enchantability;
        private SoundEvent sound;
        private Ingredient repairMaterial;
        private float toughness;
        
        private Builder() 
        {
            this.sound = SoundEvents.ARMOR_EQUIP_GENERIC;
        }
        
        public Builder withName(String name)
        {
            this.name = name;
            return this;
        }
        
        public Builder withDurabilityFactor(int durabilityFactor)
        {
            this.durabilityFactor = durabilityFactor;
            return this;
        }
        
        public Builder withDamageReductionAmounts(int[] damageReductionAmount)
        {
            this.damageReductionAmounts = damageReductionAmount;
            return this;
        }
        
        public Builder withEnchantability(int enchantability)
        {
            this.enchantability = enchantability;
            return this;
        }
        
        public Builder withRepairMaterial(Ingredient repairMaterial) 
        {
            this.repairMaterial = repairMaterial;
            return this;
        }
        
        public ArmorMaterial build() 
        {
        	return new ArmorMaterial() 
        	{
				@Override
				public float getToughness() 
				{
					return Builder.this.toughness;
				}
				
				@Override
				public Ingredient getRepairIngredient()
				{
					return Builder.this.repairMaterial;
				}
				
				@Override
				public String getName()
				{
					return Builder.this.name;
				}
				
				@Override
				public float getKnockbackResistance()
				{
					return 0;
				}
				
				@Override
				public SoundEvent getEquipSound() 
				{
					return Builder.this.sound;
				}
				
				@Override
				public int getEnchantmentValue()
				{
					return Builder.this.enchantability;
				}
				
				@Override
				public int getDurabilityForSlot(EquipmentSlot p_40410_)
				{
					return IllagersArmorMaterial.DURABILITY[p_40410_.getIndex()] * Builder.this.durabilityFactor;
				}
				
				@Override
				public int getDefenseForSlot(EquipmentSlot p_40411_) 
				{
					return Builder.this.damageReductionAmounts[p_40411_.getIndex()];
				}
			};
        }
    }
}
