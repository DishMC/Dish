package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.TropicalFish;
import net.ouja.dish.entity.DishEntity;

public class DishTropicalFish extends DishEntity implements net.ouja.api.entity.passive.TropicalFish {
    private TropicalFish tropicalFish;

    public DishTropicalFish(LivingEntity entity) {
        super(entity);
        this.tropicalFish = (TropicalFish)entity;
    }

    @Override
    public net.ouja.api.entity.passive.TropicalFish.Variants getVariant() {
        return net.ouja.api.entity.passive.TropicalFish.Variants.valueOf(this.tropicalFish.getVariant().name());
    }
}
