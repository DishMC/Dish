package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Turtle;
import net.ouja.dish.entity.DishEntity;

public class DishTurtle extends DishEntity implements net.ouja.api.entity.passive.Turtle {
    private Turtle turtle;

    public DishTurtle(Entity entity) {
        super(entity);
        this.turtle = (Turtle)entity;
    }

    @Override
    public boolean hasEgg() {
        return this.turtle.hasEgg();
    }

    @Override
    public boolean isLayingEgg() {
        return this.turtle.isLayingEgg();
    }
}
