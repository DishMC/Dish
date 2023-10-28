package net.ouja.dish.entity.neutral;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Wolf;
import net.ouja.dish.entity.DishEntity;

public class DishWolf extends DishEntity implements net.ouja.api.entity.neutral.Wolf {
    private Wolf wolf;

    public DishWolf(Entity entity) {
        super(entity);
        this.wolf = (Wolf)entity;
    }

    @Override
    public boolean isWet() {
        return this.wolf.isWet();
    }

    @Override
    public float getTailAngle() {
        return this.wolf.getTailAngle();
    }

    @Override
    public boolean isInterested() {
        return this.wolf.isInterested();
    }
}
