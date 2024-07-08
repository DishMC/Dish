package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.ouja.dish.entity.DishEntity;

public class DishSheep extends DishEntity implements net.ouja.api.entity.passive.Sheep {
    private final Sheep sheep;

    public DishSheep(LivingEntity entity) {
        super(entity);
        this.sheep = (Sheep)entity;
    }

    @Override
    public boolean isSheared() {
        return this.sheep.isSheared();
    }

    @Override
    public void setSheared(boolean b) {
        this.sheep.setSheared(b);
    }
}
