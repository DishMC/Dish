package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.Mule;
import net.ouja.dish.entity.DishEntity;

public class DishMule extends DishEntity implements net.ouja.api.entity.passive.Mule {
    private Mule mule;

    public DishMule(Entity entity) {
        super(entity);
        this.mule = (Mule)entity;
    }
}
