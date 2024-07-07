package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.MagmaCube;
import net.ouja.dish.entity.DishEntity;

public class DishMagmaCube extends DishEntity implements net.ouja.api.entity.monster.MagmaCube {
    private MagmaCube magmaCube;

    public DishMagmaCube(LivingEntity entity) {
        super(entity);
        this.magmaCube = (MagmaCube)entity;
    }
}
