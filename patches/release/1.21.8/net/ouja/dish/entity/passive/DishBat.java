package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.ouja.dish.entity.DishEntity;

public class DishBat extends DishEntity implements net.ouja.api.entity.passive.Bat {
    private Bat bat;

    public DishBat(LivingEntity entity) {
        super(entity);
        this.bat = (Bat)entity;
    }

    @Override
    public boolean isResting() {
        return this.bat.isResting();
    }

    @Override
    public void setResting(boolean b) {
        this.bat.setResting(b);
    }
}
