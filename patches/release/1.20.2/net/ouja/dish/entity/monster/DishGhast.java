package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ghast;
import net.ouja.dish.entity.DishEntity;

public class DishGhast extends DishEntity implements net.ouja.api.entity.monster.Ghast {
    private Ghast ghast;

    public DishGhast(Entity entity) {
        super(entity);
        this.ghast = (Ghast)entity;
    }

    @Override
    public boolean isCharged() {
        return this.ghast.isCharging();
    }

    @Override
    public void setCharged(boolean b) {
        this.ghast.setCharging(b);
    }
}
