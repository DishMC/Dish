package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.ouja.dish.entity.DishEntity;

public class DishElderGuardian extends DishEntity implements net.ouja.api.entity.monster.ElderGuardian {
    private ElderGuardian elderGuardian;

    public DishElderGuardian(LivingEntity entity) {
        super(entity);
        this.elderGuardian = (ElderGuardian)entity;
    }
}
