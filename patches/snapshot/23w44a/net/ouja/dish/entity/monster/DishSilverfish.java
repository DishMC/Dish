package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Silverfish;
import net.ouja.dish.entity.DishEntity;

public class DishSilverfish extends DishEntity implements net.ouja.api.entity.monster.Silverfish {
    private Silverfish silverfish;

    public DishSilverfish(Entity entity) {
        super(entity);
        this.silverfish = (Silverfish)entity;
    }
}
