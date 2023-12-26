package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Husk;
import net.ouja.dish.entity.DishEntity;

public class DishHusk extends DishEntity implements net.ouja.api.entity.monster.Husk {
    private Husk husk;

    public DishHusk(LivingEntity entity) {
        super(entity);
        this.husk = (Husk)entity;
    }
}
