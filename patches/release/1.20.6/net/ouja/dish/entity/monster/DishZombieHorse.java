package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.ouja.dish.entity.DishEntity;

public class DishZombieHorse extends DishEntity implements net.ouja.api.entity.monster.ZombieHorse {
    private ZombieHorse zombieHorse;

    public DishZombieHorse(LivingEntity entity) {
        super(entity);
        this.zombieHorse = (ZombieHorse)entity;
    }
}
