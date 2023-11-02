package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.ouja.api.entity.passive.SnifferState;
import net.ouja.dish.entity.DishEntity;

public class DishSniffer extends DishEntity implements net.ouja.api.entity.passive.Sniffer {
    private Sniffer sniffer;

    public DishSniffer(Entity entity) {
        super(entity);
        this.sniffer = (Sniffer)entity;
    }

    @Override
    public boolean isPanicked() {
        return this.sniffer.isPanicking();
    }

    @Override
    public boolean isSearching() {
        return this.sniffer.isSearching();
    }

    @Override
    public boolean isTempted() {
        return this.sniffer.isTempted();
    }

    @Override
    public SnifferState getState() {
        return SnifferState.valueOf(this.sniffer.getState().name());
    }
}
