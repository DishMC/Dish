package net.ouja.dish.entity.neutral;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Panda;
import net.ouja.dish.entity.DishEntity;

public class DishPanda extends DishEntity implements net.ouja.api.entity.neutral.Panda {
    private Panda panda;

    public DishPanda(LivingEntity entity) {
        super(entity);
        this.panda = (Panda)entity;
    }

    @Override
    public boolean isSitting() {
        return this.panda.isSitting();
    }

    @Override
    public boolean isSneezing() {
        return this.panda.isSneezing();
    }

    @Override
    public boolean isOnBack() {
        return this.panda.isOnBack();
    }

    @Override
    public boolean isEating() {
        return this.panda.isEating();
    }

    @Override
    public boolean isRolling() {
        return this.panda.isRolling();
    }

    @Override
    public void sneeze(boolean b) {
        this.panda.sneeze(b);
    }
}
