package net.ouja.dish.entity.passive;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Strider;
import net.ouja.dish.entity.DishEntity;

public class DishStrider extends DishEntity implements net.ouja.api.entity.passive.Strider {
    private Strider strider;

    public DishStrider(LivingEntity entity) {
        super(entity);
        this.strider = (Strider)entity;
    }

    @Override
    public boolean canSaddle() {
        return this.strider.isSaddleable();
    }

    @Override
    public boolean isSaddled() {
        return this.strider.isSaddled();
    }

    @Override
    public void equipSaddle() {
        this.strider.equipSaddle(null, SoundSource.BLOCKS);
    }
}
