package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.ouja.dish.entity.DishEntity;

public class DishTadpole extends DishEntity implements net.ouja.api.entity.passive.Tadpole {
    private Tadpole tadpole;

    public DishTadpole(Entity entity) {
        super(entity);
        this.tadpole = (Tadpole)entity;
    }
}
