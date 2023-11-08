package net.ouja.dish.entity.neutral;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.PolarBear;
import net.ouja.dish.entity.DishEntity;

public class DishPolarBear extends DishEntity implements net.ouja.api.entity.neutral.PolarBear {
    private PolarBear polarBear;

    public DishPolarBear(Entity entity) {
        super(entity);
        this.polarBear = (PolarBear)entity;
    }

    @Override
    public boolean isStanding() {
        return this.polarBear.isStanding();
    }

    @Override
    public void setStanding(boolean b) {
        this.polarBear.setStanding(b);
    }
}
