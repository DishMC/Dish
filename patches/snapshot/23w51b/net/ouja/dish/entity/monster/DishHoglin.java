package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.ouja.dish.entity.DishEntity;

public class DishHoglin extends DishEntity implements net.ouja.api.entity.monster.Hoglin {
    private Hoglin hoglin;

    public DishHoglin(LivingEntity entity) {
        super(entity);
        this.hoglin = (Hoglin)entity;
    }

    @Override
    public void setImmuneToZombification(boolean b) {
        this.hoglin.setImmuneToZombification(b);
    }

    @Override
    public boolean isImmuneToZombification() {
        return this.hoglin.isImmuneToZombification();
    }

    @Override
    public boolean isConverting() {
        return this.hoglin.isConverting();
    }
}
