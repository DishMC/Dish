package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.ouja.dish.entity.DishEntity;

public class DishWanderingTrader extends DishEntity implements net.ouja.api.entity.passive.WanderingTrader {
    private WanderingTrader wanderingTrader;

    public DishWanderingTrader(Entity entity) {
        super(entity);
        this.wanderingTrader = (WanderingTrader)entity;
    }
}
