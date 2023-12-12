package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pufferfish;
import net.ouja.dish.entity.DishEntity;

public class DishPufferfish extends DishEntity implements net.ouja.api.entity.passive.Pufferfish {
    private Pufferfish pufferfish;

    public DishPufferfish(LivingEntity entity) {
        super(entity);
        this.pufferfish = (Pufferfish)entity;
    }

    @Override
    public int getPuffState() {
        return this.pufferfish.getPuffState();
    }
}
