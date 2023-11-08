package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Rabbit;
import net.ouja.api.entity.passive.RabbitVariants;
import net.ouja.dish.entity.DishEntity;

public class DishRabbit extends DishEntity implements net.ouja.api.entity.passive.Rabbit {
    private Rabbit rabbit;

    public DishRabbit(Entity entity) {
        super(entity);
        this.rabbit = (Rabbit)entity;
    }

    @Override
    public RabbitVariants getVariant() {
        return RabbitVariants.valueOf(this.rabbit.getVariant().name());
    }
}
