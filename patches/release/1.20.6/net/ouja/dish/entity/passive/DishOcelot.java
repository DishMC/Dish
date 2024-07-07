package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Ocelot;
import net.ouja.dish.entity.DishEntity;

public class DishOcelot extends DishEntity implements net.ouja.api.entity.passive.Ocelot {
    private Ocelot ocelot;

    public DishOcelot(LivingEntity entity) {
        super(entity);
        this.ocelot = (Ocelot)entity;
    }
}
