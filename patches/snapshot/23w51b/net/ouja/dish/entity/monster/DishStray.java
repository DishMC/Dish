package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Stray;
import net.ouja.dish.entity.DishEntity;

public class DishStray extends DishEntity implements net.ouja.api.entity.monster.Stray {
    private Stray stray;

    public DishStray(LivingEntity entity) {
        super(entity);
        this.stray = (Stray)entity;
    }
}
