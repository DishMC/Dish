package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Evoker;
import net.ouja.dish.entity.DishEntity;

public class DishEvoker extends DishEntity implements net.ouja.api.entity.monster.Evoker {
    private Evoker evoker;

    public DishEvoker(LivingEntity entity) {
        super(entity);
        this.evoker = (Evoker)entity;
    }

    @Override
    public boolean isCastingSpell() {
        return this.evoker.isCastingSpell();
    }
}
