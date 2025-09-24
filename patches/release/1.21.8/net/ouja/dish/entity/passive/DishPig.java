package net.ouja.dish.entity.passive;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.ouja.dish.entity.DishEntity;

public class DishPig extends DishEntity implements net.ouja.api.entity.passive.Pig {
    private Pig pig;

    public DishPig(LivingEntity entity) {
        super(entity);
        this.pig = (Pig)entity;
    }

    @Override
    public boolean canSaddle() {
        return true;
    }

    @Override
    public boolean isSaddled() {
        return this.pig.isSaddled();
    }

    @Override
    public void equipSaddle() {
        this.pig.setItemSlot(EquipmentSlot.SADDLE, new ItemStack(Items.SADDLE));
    }
}
