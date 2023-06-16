package com.min01.guardillagers.event;

import com.min01.guardillagers.entity.GuardIllagerEntity;
import com.min01.guardillagers.init.IllagerEntityRegistry;

import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntityEventHandler
{
    @SubscribeEvent
    public void onEntityJoin(EntityJoinLevelEvent event)
    {
        Level world = event.getLevel();
        if (event.getEntity() instanceof AbstractVillager) 
        {
        	AbstractVillager villager = (AbstractVillager) event.getEntity();
            villager.goalSelector.addGoal(1, new AvoidEntityGoal<GuardIllagerEntity>(villager, GuardIllagerEntity.class, 16.0f, 0.8, 0.8));
        }
        if (event.getEntity() instanceof Pillager)
        {
        	Pillager pillager = (Pillager) event.getEntity();
            if (pillager.hasActiveRaid() && pillager.getCurrentRaid() != null && !pillager.isPatrolLeader() && world.random.nextInt(8) == 0)
            {
                for (int i = 0; i < 1 + world.random.nextInt(1); ++i)
                {
                    GuardIllagerEntity guardilalger = (GuardIllagerEntity) IllagerEntityRegistry.GUARD_ILLAGER.get().create(world);
                    pillager.getCurrentRaid().joinRaid(pillager.getCurrentRaid().getNumGroups(world.getDifficulty()), guardilalger, pillager.blockPosition(), false);
                }
                pillager.discard();
            }
        }
    }
}	
