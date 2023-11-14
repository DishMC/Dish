package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.MushroomCow;
import net.ouja.api.entity.passive.MooshroomCow;
import net.ouja.api.entity.passive.MooshroomCowTypes;
import net.ouja.dish.entity.DishEntity;

public class DishMushroomCow extends DishEntity implements MooshroomCow {
    private MushroomCow mushroomCow;

    public DishMushroomCow(Entity entity) {
        super(entity);
        this.mushroomCow = (MushroomCow)entity;
        this.mushroomCow.getVariant();
    }

    @Override
    public MooshroomCowTypes getVariant() {
        return MooshroomCowTypes.valueOf(this.mushroomCow.getVariant().name());
    }
}
