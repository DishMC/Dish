package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.ouja.dish.entity.DishEntity;

public class DishVillager extends DishEntity implements net.ouja.api.entity.passive.Villager {
    private Villager villager;

    public DishVillager(LivingEntity entity) {
        super(entity);
        this.villager = (Villager)entity;
    }
}
