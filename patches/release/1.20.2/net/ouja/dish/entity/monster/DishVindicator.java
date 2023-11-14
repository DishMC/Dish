package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Vindicator;
import net.ouja.dish.entity.DishEntity;

public class DishVindicator extends DishEntity implements net.ouja.api.entity.monster.Vindicator {
    private Vindicator vindicator;

    public DishVindicator(Entity entity) {
        super(entity);
        this.vindicator = (Vindicator)entity;
    }

    @Override
    public boolean isJohnny() {
        return this.vindicator.isJohnny();
    }
}
