package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Zoglin;
import net.ouja.dish.entity.DishEntity;

public class DishZoglin extends DishEntity implements net.ouja.api.entity.monster.Zoglin {
    private Zoglin zoglin;

    public DishZoglin(Entity entity) {
        super(entity);
        this.zoglin = (Zoglin)entity;
    }
}
