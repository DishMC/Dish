package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.ouja.dish.entity.DishEntity;

public class DishBlaze extends DishEntity implements net.ouja.api.entity.monster.Blaze {
    private Blaze blaze;

    public DishBlaze(LivingEntity entity) {
        super(entity);
        this.blaze = (Blaze)entity;
    }

    @Override
    public boolean isOnFire() {
        return this.blaze.isOnFire();
    }
}
