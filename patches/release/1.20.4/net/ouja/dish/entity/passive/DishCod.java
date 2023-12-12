package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cod;
import net.ouja.dish.entity.DishEntity;

public class DishCod extends DishEntity implements net.ouja.api.entity.passive.Cod {
    private Cod cod;

    public DishCod(LivingEntity entity) {
        super(entity);
        this.cod = (Cod)entity;
    }
}
