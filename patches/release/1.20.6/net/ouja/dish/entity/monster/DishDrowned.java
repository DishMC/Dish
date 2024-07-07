package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Drowned;
import net.ouja.dish.entity.DishEntity;

public class DishDrowned extends DishEntity implements net.ouja.api.entity.monster.Drowned {
    private Drowned drowned;

    public DishDrowned(LivingEntity entity) {
        super(entity);
        this.drowned = (Drowned)entity;
    }

    @Override
    public boolean isSwimming() {
        return this.drowned.isVisuallySwimming();
    }
}
