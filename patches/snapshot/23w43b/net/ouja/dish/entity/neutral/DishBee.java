package net.ouja.dish.entity.neutral;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import net.ouja.dish.entity.DishEntity;

public class DishBee extends DishEntity implements net.ouja.api.entity.neutral.Bee {
    private Bee bee;

    public DishBee(Entity entity) {
        super(entity);
        this.bee = (Bee)entity;
    }

    @Override
    public boolean hasNectar() {
        return this.bee.hasNectar();
    }

    @Override
    public boolean hasStung() {
        return this.bee.hasStung();
    }

    @Override
    public boolean isAngry() {
        return this.bee.isAngry();
    }
}
