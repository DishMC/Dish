package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Endermite;
import net.ouja.dish.entity.DishEntity;

public class DishEndermite extends DishEntity implements net.ouja.api.entity.monster.Endermite {
    private Endermite endermite;

    public DishEndermite(Entity entity) {
        super(entity);
        this.endermite = (Endermite)entity;
    }
}
