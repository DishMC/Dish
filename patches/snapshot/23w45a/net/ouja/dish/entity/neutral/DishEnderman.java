package net.ouja.dish.entity.neutral;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.EnderMan;
import net.ouja.dish.entity.DishEntity;

public class DishEnderman extends DishEntity {
    private EnderMan enderMan;

    public DishEnderman(Entity entity) {
        super(entity);
        this.enderMan = (EnderMan)entity;
    }
}
