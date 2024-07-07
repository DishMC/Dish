package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.ouja.dish.entity.DishEntity;

public class DishGuardian extends DishEntity implements net.ouja.api.entity.monster.Guardian {
    private Guardian guardian;

    public DishGuardian(LivingEntity entity) {
        super(entity);
        this.guardian = (Guardian)entity;
    }
}
