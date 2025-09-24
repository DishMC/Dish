package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.allay.Allay;
import net.ouja.dish.entity.DishEntity;

public class DishAllay extends DishEntity implements net.ouja.api.entity.passive.Allay {
    private Allay allay;

    public DishAllay(LivingEntity entity) {
        super(entity);
        this.allay = (Allay)entity;
    }
}
