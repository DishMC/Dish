package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Parrot;
import net.ouja.api.entity.passive.ParrotTypes;
import net.ouja.dish.entity.DishEntity;

public class DishParrot extends DishEntity implements net.ouja.api.entity.passive.Parrot {
    private Parrot parrot;

    public DishParrot(Entity entity) {
        super(entity);
        this.parrot = (Parrot)entity;
    }

    @Override
    public ParrotTypes getVariant() {
        return ParrotTypes.valueOf(this.parrot.getVariant().name());
    }
}
