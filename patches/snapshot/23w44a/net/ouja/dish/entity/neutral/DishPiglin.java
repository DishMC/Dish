package net.ouja.dish.entity.neutral;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.ouja.dish.entity.DishEntity;

public class DishPiglin extends DishEntity implements net.ouja.api.entity.neutral.Piglin {
    private Piglin piglin;

    public DishPiglin(Entity entity) {
        super(entity);
        this.piglin = (Piglin)entity;
    }

    @Override
    public boolean canHunt() {
        return this.piglin.canHunt();
    }

    @Override
    public void setCanHunt(boolean b) {
        this.piglin.setCannotHunt(b);
    }

    @Override
    public boolean isDancing() {
        return this.piglin.isDancing();
    }

    @Override
    public void setDancing(boolean b) {
        this.piglin.setDancing(b);
    }
}
