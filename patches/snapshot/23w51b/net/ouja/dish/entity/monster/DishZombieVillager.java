package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombieVillager;

public class DishZombieVillager extends DishZombie implements net.ouja.api.entity.monster.ZombieVillager {
    private ZombieVillager zombieVillager;

    public DishZombieVillager(LivingEntity entity) {
        super(entity);
        this.zombieVillager = (ZombieVillager)entity;
    }
}
