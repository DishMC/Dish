package net.ouja.dish.entity.neutral;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.ouja.dish.entity.DishEntity;

public class DishZombifiedPiglin extends DishEntity implements net.ouja.api.entity.neutral.ZombifiedPiglin {
    private ZombifiedPiglin zombifiedPiglin;

    public DishZombifiedPiglin(Entity entity) {
        super(entity);
        this.zombifiedPiglin = (ZombifiedPiglin)entity;
    }
}
