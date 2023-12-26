package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.ouja.dish.entity.DishEntity;

public class DishSnowGolem extends DishEntity implements net.ouja.api.entity.passive.SnowGolem {
    private SnowGolem snowGolem;

    public DishSnowGolem(LivingEntity entity) {
        super(entity);
        this.snowGolem = (SnowGolem)entity;
    }

    @Override
    public boolean isWearingPumpkin() {
        return this.snowGolem.hasPumpkin();
    }

    @Override
    public void setWearingPumpkin(boolean b) {
        this.snowGolem.setPumpkin(b);
    }
}
