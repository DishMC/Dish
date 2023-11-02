package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Cow;
import net.ouja.dish.entity.DishEntity;

public class DishCow extends DishEntity implements net.ouja.api.entity.passive.Cow {
    private Cow cow;

    public DishCow(Entity entity) {
        super(entity);
        this.cow = (Cow)entity;
    }
}
