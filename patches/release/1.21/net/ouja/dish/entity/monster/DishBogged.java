package net.ouja.dish.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Bogged;
import net.ouja.dish.entity.DishEntity;

public class DishBogged extends DishEntity implements net.ouja.api.entity.monster.Bogged {
  private final Bogged bogged;

  public DishBogged(LivingEntity bogged) {
	  super(bogged);
	  this.bogged = (Bogged)bogged;
  }

  @Override
  public boolean isSheared() {
	  return this.bogged.isSheared();
  }

  @Override
  public void setSheared(boolean b) {
	  this.bogged.setSheared(b);
  }
}
