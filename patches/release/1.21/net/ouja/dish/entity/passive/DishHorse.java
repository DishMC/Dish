package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.ouja.api.entity.passive.HorseMarkings;
import net.ouja.api.entity.passive.HorseTypes;
import net.ouja.dish.entity.DishEntity;

public class DishHorse extends DishEntity implements net.ouja.api.entity.passive.Horse {
    private Horse horse;

    public DishHorse(LivingEntity entity) {
        super(entity);
        this.horse = (Horse)entity;
    }

    @Override
    public HorseTypes getVariant() {
        return HorseTypes.valueOf(this.horse.getVariant().name());
    }

    @Override
    public HorseMarkings getMarkings() {
        return HorseMarkings.valueOf(this.horse.getMarkings().name());
    }
}
