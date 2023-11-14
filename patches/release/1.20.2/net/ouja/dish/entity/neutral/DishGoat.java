package net.ouja.dish.entity.neutral;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.goat.Goat;
import net.ouja.dish.entity.DishEntity;

public class DishGoat extends DishEntity implements net.ouja.api.entity.neutral.Goat {
    private Goat goat;

    public DishGoat(Entity entity) {
        super(entity);
        this.goat = (Goat)entity;
    }

    @Override
    public boolean hasLeftHorn() {
        return this.goat.hasLeftHorn();
    }

    @Override
    public boolean hasRightHorn() {
        return this.goat.hasRightHorn();
    }

    @Override
    public void addHorns() {
        this.goat.addHorns();
    }

    @Override
    public void removeHorns() {
        this.goat.removeHorns();
    }

    @Override
    public boolean isScreamingGoat() {
        return this.goat.isScreamingGoat();
    }
}
