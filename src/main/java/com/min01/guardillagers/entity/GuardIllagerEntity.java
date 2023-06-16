package com.min01.guardillagers.entity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.min01.guardillagers.GuardIllagers;
import com.min01.guardillagers.init.IllagerSoundsRegister;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GuardIllagerEntity extends AbstractIllager
{
    private static final UUID MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
    private static final AttributeModifier MODIFIER = new AttributeModifier(GuardIllagerEntity.MODIFIER_UUID, "Drinking speed penalty", -0.24, AttributeModifier.Operation.ADDITION);
    private static final EntityDataAccessor<Boolean> IS_DRINKING = SynchedEntityData.defineId(GuardIllagerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_GUARD = SynchedEntityData.defineId(GuardIllagerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> GUARD_LEVEL = SynchedEntityData.defineId(GuardIllagerEntity.class, EntityDataSerializers.INT);
    private int itemUseTimer;
    public double prevCapeX;
    public double prevCapeY;
    public double prevCapeZ;
    public double capeX;
    public double capeY;
    public double capeZ;
    
	public GuardIllagerEntity(EntityType<? extends AbstractIllager> p_32105_, Level p_32106_)
	{
		super(p_32105_, p_32106_);
        this.setDropChance(EquipmentSlot.OFFHAND, 0.4f);
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        this.xpReward = 6;
	}
	
	@Override
    protected void registerGoals() 
    {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.75));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[] { Raider.class }).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, false).setUnseenMemoryTicks(300));
    }
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return AbstractIllager.createMonsterAttributes()
    			.add(Attributes.MOVEMENT_SPEED, 0.3479999899864197)
    			.add(Attributes.FOLLOW_RANGE, 22.0)
        		.add(Attributes.MAX_HEALTH, 24.0)
        		.add(Attributes.ATTACK_DAMAGE, 3.0)
        		.add(Attributes.ARMOR, 6.0);
    }
	
	@Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.getEntityData().define(GuardIllagerEntity.IS_DRINKING, false);
        this.getEntityData().define(GuardIllagerEntity.IS_GUARD, false);
        this.getEntityData().define(GuardIllagerEntity.GUARD_LEVEL, 1);
    }
    
    public void setDrinkingPotion(boolean drinkingPotion) 
    {
        this.getEntityData().set(GuardIllagerEntity.IS_DRINKING, drinkingPotion);
    }
    
    public boolean isDrinkingPotion() 
    {
        return this.getEntityData().get(GuardIllagerEntity.IS_DRINKING);
    }
    
    public void setGuardSelf(boolean guardSelf) 
    {
        this.getEntityData().set(GuardIllagerEntity.IS_GUARD, guardSelf);
    }
    
    public boolean isGuardSelf() 
    {
        return this.getEntityData().get(GuardIllagerEntity.IS_GUARD);
    }
    
    public int getGuardLevel() 
    {
        return this.entityData.get(GuardIllagerEntity.GUARD_LEVEL);
    }
    
    public void setGuardLevel(final int level)
    {
        this.entityData.set(GuardIllagerEntity.GUARD_LEVEL, level);
        if (level > 0) 
        {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(24.0 + level * 2);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0 + level * 0.5f);
            this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(22.0 + level);
            this.getAttribute(Attributes.ARMOR).setBaseValue(7.0);
        }
        if (level == 2)
        {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.36800000071525574);
        }
    }
    
    @Override
    public void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);
        compound.putInt("GuardLevel", this.getGuardLevel());
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag compound)
    {
        super.readAdditionalSaveData(compound);
        this.setGuardLevel(compound.getInt("GuardLevel"));
    }
    
    @Override
    public void aiStep() 
    {
        if (!this.level.isClientSide) 
        {
            if (this.isDrinkingPotion()) 
            {
                this.setGuardSelf(false);
                if (this.itemUseTimer-- <= 0)
                {
                    this.setDrinkingPotion(false);
                    ItemStack itemstack = this.getOffhandItem();
                    if (this.getGuardLevel() >= 1) 
                    {
                        this.setItemSlot(EquipmentSlot.OFFHAND, getIllagerShield());
                    }
                    else 
                    {
                        this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }
                    if (itemstack.getItem() == Items.POTION) 
                    {
                        List<MobEffectInstance> list = PotionUtils.getCustomEffects(itemstack);
                        if (list != null) 
                        {
                            for (MobEffectInstance potioneffect : list) 
                            {
                                this.addEffect(new MobEffectInstance(potioneffect));
                            }
                        }
                    }
                    this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(GuardIllagerEntity.MODIFIER);
                }
            }
            else if (this.isGuardSelf()) 
            {
                if (this.itemUseTimer-- <= 0) 
                {
                    this.setGuardSelf(false);
                    this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(GuardIllagerEntity.MODIFIER);
                    this.stopUsingItem();
                }
            }
            else 
            {
                Potion potiontype = null;
                if (this.random.nextFloat() < 0.005f && this.getHealth() < this.getMaxHealth()) 
                {
                    potiontype = Potions.HEALING;
                }
                else if (this.random.nextFloat() < 0.01f && this.getTarget() != null && !this.hasEffect(MobEffects.MOVEMENT_SPEED) && this.getTarget().distanceToSqr((Entity)this) > 121.0) 
                {
                    potiontype = Potions.SWIFTNESS;
                }
                if (potiontype != null)
                {
                    this.stopUsingItem();
                    this.setItemSlot(EquipmentSlot.OFFHAND, PotionUtils.setPotion(new ItemStack(Items.POTION), potiontype));
                    this.itemUseTimer = this.getOffhandItem().getUseDuration();
                    this.setDrinkingPotion(true);
                    this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITCH_DRINK, this.getSoundSource(), 1.0f, 0.8f + this.random.nextFloat() * 0.4f);
                    AttributeInstance iattributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
                    iattributeinstance.removeModifier(GuardIllagerEntity.MODIFIER);
                    iattributeinstance.addPermanentModifier(GuardIllagerEntity.MODIFIER);
                }
                else if (this.getOffhandItem().getItem() instanceof ShieldItem && this.random.nextFloat() < 0.0055f && this.getTarget() != null) 
                {
                    this.itemUseTimer = 100;
                    this.setGuardSelf(true);
                    this.startUsingItem(InteractionHand.OFF_HAND);
                    AttributeInstance iattributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
                    iattributeinstance.removeModifier(GuardIllagerEntity.MODIFIER);
                    iattributeinstance.addPermanentModifier(GuardIllagerEntity.MODIFIER);
                }
                else 
                {
                    this.stopUsingItem();
                }
            }
        }
        super.aiStep();
    }
    
    @Override
    public void tick() 
    {
        super.tick();
        this.updateCape();
    }
    
    private void updateCape() 
    {
        double elasticity = 0.25;
        double gravity = -0.1;
        this.prevCapeX = this.capeX;
        this.prevCapeY = this.capeY;
        this.prevCapeZ = this.capeZ;
        this.capeY += gravity;
        this.capeX += (this.getX() - this.capeX) * elasticity;
        this.capeY += (this.getY() - this.capeY) * elasticity;
        this.capeZ += (this.getZ() - this.capeZ) * elasticity;
    }
    
    @Override
    public void setLastHurtByMob(@Nullable LivingEntity livingBase) 
    {
        super.setLastHurtByMob(livingBase);
        if (livingBase != null && livingBase instanceof Player && !((Player)livingBase).isCreative() && this.isAlive()) 
        {
            this.level.broadcastEntityEvent(this, (byte)13);
        }
    }
    
    @Override
    public boolean hurt(DamageSource source, float amount)
    {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (source.isProjectile())
        {
            return super.hurt(source, amount * 0.95f);
        }
        if (this.canBlockDamageSource(source))
        {
            this.playSound(SoundEvents.SHIELD_BLOCK, 1.0f, 0.8f + this.level.random.nextFloat() * 0.4f);
            return false;
        }
        return super.hurt(source, amount);
    }
    
    private boolean canBlockDamageSource(DamageSource damageSourceIn)
    {
        Entity entity = damageSourceIn.getDirectEntity();
        boolean flag = false;
        if (entity instanceof AbstractArrow) 
        {
        	AbstractArrow abstractarrowentity = (AbstractArrow) entity;
            if (abstractarrowentity.getPierceLevel() > 0) 
            {
                flag = true;
            }
        }
        if (!damageSourceIn.isBypassArmor() && this.isBlocking() && !flag) 
        {
        	Vec3 vec3d2 = damageSourceIn.getSourcePosition();
            if (vec3d2 != null)
            {
                Vec3 vec3d3 = this.getViewVector(1.0f);
                Vec3 vec3d4 = vec3d2.vectorTo(this.position()).normalize();
                vec3d4 = new Vec3(vec3d4.x, 0.0, vec3d4.z);
                if (vec3d4.dot(vec3d3) < 0.0) 
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    protected ResourceLocation getDefaultLootTable() 
    {
    	return GuardIllagers.LOOT_TABLE;
    }
    
    @OnlyIn(Dist.CLIENT)
    public IllagerArmPose getArmPose() 
    {
        return this.isAggressive() ? IllagerArmPose.ATTACKING : IllagerArmPose.CROSSED;
    }
    
    public void setHomePos()
    {
    	this.restrictTo(new BlockPos(this.position()), 26);
    }
    
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_37856_, DifficultyInstance p_37857_, MobSpawnType p_37858_, SpawnGroupData p_37859_, CompoundTag p_37860_) 
    {
        this.setGuardLevel(this.random.nextInt(2));
        this.setEquipmentBasedOnDifficulty(p_37857_);
        this.populateDefaultEquipmentEnchantments(p_37856_.getRandom(), p_37857_);
    	return super.finalizeSpawn(p_37856_, p_37857_, p_37858_, p_37859_, p_37860_);
    }
    
    @Override
    public SoundEvent getCelebrateSound()
    {
        return SoundEvents.VINDICATOR_CELEBRATE;
    }
    
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) 
    {
        if (this.getGuardLevel() >= 1)
        {
            this.setItemSlot(EquipmentSlot.OFFHAND, getIllagerShield());
        }
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
    }
		
	@Override
    public void applyRaidBuffs(int p_213660_1_, boolean p_213660_2_)
	{
        ItemStack itemstack = new ItemStack(Items.IRON_SWORD);
        Raid raid = this.getCurrentRaid();
        int i = 1;
        if (p_213660_1_ > raid.getNumGroups(Difficulty.NORMAL))
        {
            i = 2;
        }
        boolean flag = this.random.nextFloat() <= raid.getEnchantOdds();
        boolean flag2 = this.random.nextFloat() <= 0.25;
        //boolean flag3 = this.random.nextFloat() <= 0.15;
        if (flag) 
        {
            Map<Enchantment, Integer> map = Maps.newHashMap();
            map.put(Enchantments.SHARPNESS, i);
            EnchantmentHelper.setEnchantments(map, itemstack);
            if (flag2) 
            {
                ItemStack stack2 = getIllagerShield();
                Map<Enchantment, Integer> map2 = Maps.newHashMap();
                map2.put(Enchantments.UNBREAKING, i);
                EnchantmentHelper.setEnchantments(map2, stack2);
                this.setItemSlot(EquipmentSlot.OFFHAND, stack2);
                this.setGuardLevel(3);
            }
            else 
            {
                this.setItemSlot(EquipmentSlot.OFFHAND, getIllagerShield());
                this.setGuardLevel(1 + this.random.nextInt(2));
            }
        }
        else 
        {
            this.setGuardLevel(this.random.nextInt(1));
        }
        this.setItemSlot(EquipmentSlot.MAINHAND, itemstack);
    }
	
    public static ItemStack getIllagerShield()
    {
        ItemStack banner = Raid.getLeaderBannerInstance();
        ItemStack shield = new ItemStack(Items.SHIELD, 1);
        applyBanner(banner, shield);
        return shield;
    }
    
    private static void applyBanner(ItemStack banner, ItemStack shield)
    {
    	CompoundTag bannerNBT = banner.getOrCreateTagElement("BlockEntityTag");
        CompoundTag shieldNBT = (bannerNBT == null) ? new CompoundTag() : bannerNBT.copy();
        shield.addTagElement("BlockEntityTag", shieldNBT);
    }
	
	@Override
    public boolean isAlliedTo(Entity entityIn) 
    {
        return super.isAlliedTo(entityIn) || (entityIn instanceof LivingEntity && ((LivingEntity)entityIn).getMobType() == MobType.ILLAGER && this.getTeam() == null && entityIn.getTeam() == null);
    }
    
    @Override
    protected SoundEvent getAmbientSound() 
    {
        if (this.isAggressive()) 
        {
            return IllagerSoundsRegister.GUARDILLAGER_ANGRY.get();
        }
        return IllagerSoundsRegister.GUARDILLAGER_AMBIENT.get();
    }
    
    @Override
    protected SoundEvent getDeathSound() 
    {
        return IllagerSoundsRegister.GUARDILLAGER_DIE.get();
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return IllagerSoundsRegister.GUARDILLAGER_HURT.get();
    }
    
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockState) 
    {
        SoundType soundtype = blockState.getSoundType(this.level, pos, this);
        SoundEvent soundEvent = IllagerSoundsRegister.GUARDILLAGER_STEP.get();
        if (soundtype == SoundType.SNOW)
        {
            this.playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.15f, soundtype.getPitch());
            this.playSound(soundEvent, 0.2f, soundtype.getPitch());
        }
        else if (!blockState.getMaterial().isLiquid()) 
        {
            this.playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.15f, soundtype.getPitch());
            this.playSound(soundEvent, 0.2f, soundtype.getPitch());
        }
    }
}
