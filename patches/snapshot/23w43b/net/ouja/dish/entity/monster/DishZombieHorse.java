package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.ouja.dish.entity.DishEntity;

public class DishZombieHorse extends DishEntity implements net.ouja.api.entity.monster.ZombieHorse {
    private ZombieHorse zombieHorse;

    public DishZombieHorse(Entity entity) {
        super(entity);
        this.zombieHorse = (ZombieHorse)entity;
    }
}
