package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Witch;
import net.ouja.dish.entity.DishEntity;

public class DishWitch extends DishEntity implements net.ouja.api.entity.monster.Witch {
    private Witch witch;

    public DishWitch(Entity entity) {
        super(entity);
        this.witch = (Witch)entity;
    }
}
