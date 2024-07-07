package net.ouja.dish.entity.passive;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.ouja.dish.entity.DishEntity;

public class DishPig extends DishEntity implements net.ouja.api.entity.passive.Pig {
    private Pig pig;

    public DishPig(LivingEntity entity) {
        super(entity);
        this.pig = (Pig)entity;
    }

    @Override
    public boolean canSaddle() {
        return this.pig.isSaddleable();
    }

    @Override
    public boolean isSaddled() {
        return this.pig.isSaddled();
    }

    @Override
    public void equipSaddle() {
        this.pig.equipSaddle(SoundSource.BLOCKS);
    }
}
