package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.ouja.dish.entity.DishEntity;

public class DishPhantom extends DishEntity implements net.ouja.api.entity.monster.Phantom {
    private Phantom phantom;

    public DishPhantom(LivingEntity entity) {
        super(entity);
        this.phantom = (Phantom)entity;
    }
}
