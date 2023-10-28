package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Squid;
import net.ouja.dish.entity.DishEntity;

public class DishSquid extends DishEntity implements net.ouja.api.entity.passive.Squid {
    private Squid squid;

    public DishSquid(Entity entity) {
        super(entity);
        this.squid = (Squid)entity;
    }

    @Override
    public void ink() {
        this.squid.spawnInk();
    }
}
