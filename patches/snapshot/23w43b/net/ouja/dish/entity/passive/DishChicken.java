package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Chicken;
import net.ouja.dish.entity.DishEntity;

public class DishChicken extends DishEntity implements net.ouja.api.entity.passive.Chicken {
    private Chicken chicken;

    public DishChicken(Entity entity) {
        super(entity);
        this.chicken = (Chicken)entity;
    }
}
