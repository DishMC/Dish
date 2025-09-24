package net.ouja.dish.entity.passive;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.ouja.dish.entity.DishEntity;

public class DishStrider extends DishEntity implements net.ouja.api.entity.passive.Strider {
    private Strider strider;

    public DishStrider(LivingEntity entity) {
        super(entity);
        this.strider = (Strider)entity;
    }

    @Override
    public boolean canSaddle() {
        return true;
    }

    @Override
    public boolean isSaddled() {
        return this.strider.isSaddled();
    }

    @Override
    public void equipSaddle() {
        this.strider.setItemSlot(EquipmentSlot.SADDLE, new ItemStack(Items.SADDLE));
    }
}
