package net.ouja.dish.entity.neutral;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.TraderLlama;
import net.ouja.dish.entity.DishEntity;

public class DishTraderLlama extends DishEntity implements net.ouja.api.entity.neutral.TraderLlama {
    private TraderLlama traderLlama;

    public DishTraderLlama(Entity entity) {
        super(entity);
        this.traderLlama = (TraderLlama)entity;
    }
}
