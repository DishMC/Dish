package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.GlowSquid;

public class DishGlowSquid extends DishSquid implements net.ouja.api.entity.passive.GlowSquid {
    private GlowSquid glowSquid;

    public DishGlowSquid(LivingEntity entity) {
        super(entity);
        this.glowSquid = (GlowSquid)entity;
    }
}
