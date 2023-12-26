package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.ouja.dish.entity.DishEntity;

public class DishSlime extends DishEntity implements net.ouja.api.entity.monster.Slime {
    private Slime slime;

    public DishSlime(LivingEntity entity) {
        super(entity);
        this.slime = (Slime)entity;
    }

    @Override
    public int getSize() {
        return this.slime.getSize();
    }
}
