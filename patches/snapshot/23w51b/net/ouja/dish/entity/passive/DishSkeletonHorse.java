package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.ouja.dish.entity.DishEntity;

public class DishSkeletonHorse extends DishEntity implements net.ouja.api.entity.passive.SkeletonHorse {
    private SkeletonHorse skeletonHorse;

    public DishSkeletonHorse(LivingEntity entity) {
        super(entity);
        this.skeletonHorse = (SkeletonHorse)entity;
    }

    @Override
    public boolean isTrap() {
        return this.skeletonHorse.isTrap();
    }
}
