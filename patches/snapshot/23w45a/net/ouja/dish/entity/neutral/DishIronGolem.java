package net.ouja.dish.entity.neutral;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.IronGolem;
import net.ouja.dish.entity.DishEntity;

public class DishIronGolem extends DishEntity implements net.ouja.api.entity.neutral.IronGolem {
    private IronGolem ironGolem;

    public DishIronGolem(Entity entity) {
        super(entity);
        this.ironGolem = (IronGolem)entity;
    }

    @Override
    public boolean isPlayerCreated() {
        return this.ironGolem.isPlayerCreated();
    }

    @Override
    public Crack getCrackiness() {
        return Crack.valueOf(this.ironGolem.getCrackiness().name());
    }
}
