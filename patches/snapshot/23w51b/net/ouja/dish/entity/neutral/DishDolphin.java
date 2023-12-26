package net.ouja.dish.entity.neutral;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Dolphin;
import net.ouja.dish.entity.DishEntity;

public class DishDolphin extends DishEntity {
    private Dolphin dolphin;

    public DishDolphin(LivingEntity entity) {
        super(entity);
        this.dolphin = (Dolphin)entity;
    }
}
