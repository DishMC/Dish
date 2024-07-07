package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.ouja.dish.entity.DishEntity;

public class DishPiglinBrute extends DishEntity implements net.ouja.api.entity.monster.PiglinBrute {
    private PiglinBrute piglinBrute;

    public DishPiglinBrute(LivingEntity entity) {
        super(entity);
        this.piglinBrute = (PiglinBrute)entity;
    }
}
