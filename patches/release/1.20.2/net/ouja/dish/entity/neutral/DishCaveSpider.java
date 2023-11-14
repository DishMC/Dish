package net.ouja.dish.entity.neutral;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.CaveSpider;
import net.ouja.dish.entity.DishEntity;

public class DishCaveSpider extends DishEntity implements net.ouja.api.entity.neutral.CaveSpider {
    private CaveSpider caveSpider;

    public DishCaveSpider(Entity entity) {
        super(entity);
        this.caveSpider = (CaveSpider)entity;
    }
}
