package net.ouja.dish.entity.neutral;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.ouja.dish.entity.DishEntity;

public class DishEnderman extends DishEntity {
    private EnderMan enderMan;

    public DishEnderman(LivingEntity entity) {
        super(entity);
        this.enderMan = (EnderMan)entity;
    }
}
