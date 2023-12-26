package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Vex;
import net.ouja.dish.entity.DishEntity;
import org.jetbrains.annotations.Nullable;

public class DishVex extends DishEntity implements net.ouja.api.entity.monster.Vex {
    private Vex vex;

    public DishVex(LivingEntity entity) {
        super(entity);
        this.vex = (Vex)entity;
    }

    @Override
    @Nullable
    public net.ouja.api.Entity getOwner() {
        if (this.vex.getOwner() == null) return null;
        return new DishEntity(this.vex.getOwner());
    }
}
