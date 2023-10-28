package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ravager;
import net.ouja.dish.entity.DishEntity;

public class DishRavager extends DishEntity implements net.ouja.api.entity.monster.Ravager {
    private Ravager ravager;

    public DishRavager(Entity entity) {
        super(entity);
        this.ravager = (Ravager)entity;
    }
}
