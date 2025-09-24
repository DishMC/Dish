package net.ouja.dish.world;

import net.minecraft.world.entity.LivingEntity;
import net.ouja.api.entity.Entity;
import net.ouja.api.world.DamageSource;
import net.ouja.dish.entity.DishEntity;
import org.jetbrains.annotations.Nullable;

public class DishDamageSource implements DamageSource {
    private final net.minecraft.world.damagesource.DamageSource damageSource;

    public DishDamageSource(net.minecraft.world.damagesource.DamageSource damageSource) {
        this.damageSource = damageSource;
    }

    @Nullable
    @Override
    public Entity getEntity() {
        if (this.damageSource.getEntity() == null) return null;
        return new DishEntity((LivingEntity)this.damageSource.getEntity()).getEntity(); // call .getEntity() so that it removes "getEntity().getEntity();" for plugins
    }
}
