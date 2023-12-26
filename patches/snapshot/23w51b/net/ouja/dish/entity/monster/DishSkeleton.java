package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.ouja.dish.entity.DishEntity;

public class DishSkeleton extends DishEntity implements net.ouja.api.entity.monster.Skeleton {
    private Skeleton skeleton;

    public DishSkeleton(LivingEntity entity) {
        super(entity);
        this.skeleton = (Skeleton)entity;
    }

    @Override
    public boolean isShaking() {
        return this.skeleton.isShaking();
    }

    @Override
    public void setShaking(boolean b) {
        this.skeleton.setFreezeConverting(b);
    }
}
