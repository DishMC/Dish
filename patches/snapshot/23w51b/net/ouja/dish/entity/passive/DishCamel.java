package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.camel.Camel;
import net.ouja.dish.entity.DishEntity;

public class DishCamel extends DishEntity implements net.ouja.api.entity.passive.Camel {
    private Camel camel;

    public DishCamel(LivingEntity entity) {
        super(entity);
        this.camel = (Camel)entity;
    }

    @Override
    public boolean isSitting() {
        return this.camel.isCamelSitting();
    }

    @Override
    public boolean isDashing() {
        return this.camel.isDashing();
    }

    @Override
    public void setDashing(boolean b) {
        this.camel.setDashing(b);
    }

    @Override
    public void sitDown() {
        this.camel.sitDown();
    }

    @Override
    public void standUp() {
        this.camel.standUp();
    }
}
