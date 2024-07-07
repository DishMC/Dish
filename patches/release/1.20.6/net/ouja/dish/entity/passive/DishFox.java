package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.ouja.dish.entity.DishEntity;

public class DishFox extends DishEntity implements net.ouja.api.entity.passive.Fox {
    private Fox fox;

    public DishFox(LivingEntity entity) {
        super(entity);
        this.fox = (Fox)entity;
    }

    @Override
    public String getVariant() {
        return this.fox.getVariant().getSerializedName();
    }
}
