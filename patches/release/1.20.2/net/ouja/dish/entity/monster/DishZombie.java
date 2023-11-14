package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Zombie;
import net.ouja.dish.entity.DishEntity;

public class DishZombie extends DishEntity implements net.ouja.api.entity.monster.Zombie {
    private Zombie zombie;

    public DishZombie(Entity entity) {
        super(entity);
        this.zombie = (Zombie)entity;
    }

    @Override
    public boolean isConverting() {
        return this.zombie.isUnderWaterConverting();
    }

    @Override
    public boolean canBreakDoors() {
        return this.zombie.canBreakDoors();
    }

    @Override
    public void setCanBreakDoors(boolean b) {
        this.zombie.setCanBreakDoors(b);
    }
}
