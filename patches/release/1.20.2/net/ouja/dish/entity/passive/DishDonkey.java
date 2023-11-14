package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.ouja.dish.entity.DishEntity;

public class DishDonkey extends DishEntity implements net.ouja.api.entity.passive.Donkey {
    private Donkey donkey;

    public DishDonkey(Entity entity) {
        super(entity);
        this.donkey = (Donkey)entity;
    }
}
