package net.ouja.dish.entity.neutral;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Llama;
import net.ouja.api.entity.neutral.LlamaVariants;
import net.ouja.dish.entity.DishEntity;

public class DishLlama extends DishEntity implements net.ouja.api.entity.neutral.Llama {
    private Llama llama;

    public DishLlama(LivingEntity entity) {
        super(entity);
        this.llama = (Llama)entity;
    }

    @Override
    public LlamaVariants getVariant() {
        return LlamaVariants.valueOf(this.llama.getVariant().name());
    }
}
