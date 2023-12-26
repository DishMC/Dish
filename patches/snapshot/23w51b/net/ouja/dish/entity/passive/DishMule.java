package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Mule;
import net.ouja.dish.entity.DishEntity;

public class DishMule extends DishEntity implements net.ouja.api.entity.passive.Mule {
    private Mule mule;

    public DishMule(LivingEntity entity) {
        super(entity);
        this.mule = (Mule)entity;
    }
}
