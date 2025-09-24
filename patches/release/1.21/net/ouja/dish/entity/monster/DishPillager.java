package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Pillager;
import net.ouja.dish.entity.DishEntity;

public class DishPillager extends DishEntity implements net.ouja.api.entity.monster.Pillager {
    private Pillager pillager;

    public DishPillager(LivingEntity entity) {
        super(entity);
        this.pillager = (Pillager)entity;
    }

    @Override
    public boolean isChargingCrossbow() {
        return this.pillager.isChargingCrossbow();
    }
}
