package net.ouja.dish.entity.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.ouja.dish.entity.DishEntity;

public class DishAxolotl extends DishEntity implements net.ouja.api.entity.passive.Axolotl {
    private Axolotl axolotl;

    public DishAxolotl(LivingEntity entity) {
        super(entity);
        this.axolotl = (Axolotl)entity;
    }

    @Override
    public String getVariant() {
        return this.axolotl.getVariant().getName();
    }

    @Override
    public boolean isFromBucket() {
        return this.axolotl.fromBucket();
    }
}
