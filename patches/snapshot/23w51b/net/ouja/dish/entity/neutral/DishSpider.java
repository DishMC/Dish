package net.ouja.dish.entity.neutral;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Spider;
import net.ouja.dish.entity.DishEntity;

public class DishSpider extends DishEntity implements net.ouja.api.entity.neutral.Spider {
    private Spider spider;

    public DishSpider(LivingEntity entity) {
        super(entity);
        this.spider = (Spider)entity;
    }

    @Override
    public boolean isClimbing() {
        return this.spider.isClimbing();
    }
}
