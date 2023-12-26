package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.ouja.dish.entity.DishEntity;

public class DishCreeper extends DishEntity implements net.ouja.api.entity.monster.Creeper {
    private Creeper creeper;

    public DishCreeper(LivingEntity entity) {
        super(entity);
        this.creeper = (Creeper)entity;
    }

    @Override
    public boolean isCharged() {
        return this.creeper.isPowered();
    }

    @Override
    public boolean isIgnited() {
        return this.creeper.isIgnited();
    }

    @Override
    public void ignite() {
        this.creeper.ignite();
    }
}
