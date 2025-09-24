package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.ouja.dish.entity.DishEntity;

public class DishArmadillo extends DishEntity implements net.ouja.api.entity.passive.Armadillo {
    private final Armadillo armadillo;

    public DishArmadillo(LivingEntity entity) {
        super(entity);
        this.armadillo = (Armadillo)entity;
    }

    @Override
    public boolean isScared() {
        return this.armadillo.isScared();
    }

    @Override
    public void setScared(boolean scared) {
        this.armadillo.switchToState(scared ? Armadillo.ArmadilloState.SCARED : Armadillo.ArmadilloState.IDLE);
    }

    @Override
    public void rollUp() {
        this.armadillo.rollUp();
    }

    @Override
    public void rollOut() {
        this.armadillo.rollOut();
    }
}
