package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Salmon;
import net.ouja.dish.entity.DishEntity;

public class DishSalmon extends DishEntity implements net.ouja.api.entity.passive.Salmon {
    private Salmon salmon;

    public DishSalmon(LivingEntity entity) {
        super(entity);
        this.salmon = (Salmon)entity;
    }
}
