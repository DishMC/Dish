package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.ouja.dish.entity.DishEntity;

public class DishWarden extends DishEntity implements net.ouja.api.entity.monster.Warden {
    private Warden warden;

    public DishWarden(LivingEntity entity) {
        super(entity);
        this.warden = (Warden)entity;
    }
}
