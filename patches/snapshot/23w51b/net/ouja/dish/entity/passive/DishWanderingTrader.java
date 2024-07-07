package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.ouja.dish.entity.DishEntity;

public class DishWanderingTrader extends DishEntity implements net.ouja.api.entity.passive.WanderingTrader {
    private WanderingTrader wanderingTrader;

    public DishWanderingTrader(LivingEntity entity) {
        super(entity);
        this.wanderingTrader = (WanderingTrader)entity;
    }
}
