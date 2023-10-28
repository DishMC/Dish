package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Shulker;
import net.ouja.dish.entity.DishEntity;

public class DishShulker extends DishEntity implements net.ouja.api.entity.monster.Shulker {
    private Shulker shulker;

    public DishShulker(Entity entity) {
        super(entity);
        this.shulker = (Shulker)entity;
    }

    @Override
    public boolean isClosed() {
        return this.shulker.isClosed();
    }
}
