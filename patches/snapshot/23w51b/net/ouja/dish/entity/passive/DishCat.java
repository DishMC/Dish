package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.ouja.dish.entity.DishEntity;

public class DishCat extends DishEntity implements net.ouja.api.entity.passive.Cat {
    private Cat cat;

    public DishCat(LivingEntity entity) {
        super(entity);
        this.cat = (net.minecraft.world.entity.animal.Cat)entity;
    }

    @Override
    public String getBreed() {
        return cat.getVariant().toString();
    }

    @Override
    public boolean isLaying() {
        return cat.isLying();
    }

    @Override
    public void hiss() {
        cat.hiss();
    }

    @Override
    public void setLaying(boolean b) {
        cat.setLying(b);
    }
}
