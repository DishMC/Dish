package net.ouja.dish.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.ouja.api.Entity;
import net.ouja.api.entity.EntityTypes;
import net.ouja.dish.entity.monster.*;
import net.ouja.dish.entity.neutral.*;
import net.ouja.dish.entity.passive.*;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class DishEntity implements Entity {
    private net.minecraft.world.entity.LivingEntity entity;

    public DishEntity(net.minecraft.world.entity.LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public int getId() {
        return entity.getId();
    }

    @Override
    // todo: finish adding other entity types i.e. ChestBoat, Llama_spit, etc...
    public Entity getEntity() {
        EntityType<?> entityType = this.entity.getType();
        // Passive mobs
        if (entityType == EntityType.ALLAY) {
            return new DishAllay(this.entity);
        }
        else if (entityType == EntityType.AXOLOTL) {
            return new DishAxolotl(this.entity);
        }
        else if (entityType == EntityType.BAT) {
            return new DishBat(this.entity);
        }
        else if (entityType == EntityType.CAMEL) {
            return new DishCamel(this.entity);
        }
        else if (entityType == EntityType.CAT) {
            return new DishCat(this.entity);
        }
        else if (entityType == EntityType.CHICKEN) {
            return new DishChicken(this.entity);
        }
        else if (entityType == EntityType.COD) {
            return new DishCod(this.entity);
        }
        else if (entityType == EntityType.COW) {
            return new DishCow(this.entity);
        }
        else if (entityType == EntityType.DONKEY) {
            return new DishDonkey(this.entity);
        }
        else if (entityType == EntityType.FOX) {
            return new DishFox(this.entity);
        }
        else if (entityType == EntityType.FROG) {
            return new DishFrog(this.entity);
        }
        else if (entityType == EntityType.GLOW_SQUID) {
            return new DishGlowSquid(this.entity);
        }
        else if (entityType == EntityType.HORSE) {
            return new DishHorse(this.entity);
        }
        else if (entityType == EntityType.MOOSHROOM) {
            return new DishMushroomCow(this.entity);
        }
        else if (entityType == EntityType.MULE) {
            return new DishMule(this.entity);
        }
        else if (entityType == EntityType.OCELOT) {
            return new DishOcelot(this.entity);
        }
        else if (entityType == EntityType.PARROT) {
            return new DishParrot(this.entity);
        }
        else if (entityType == EntityType.PIG) {
            return new DishPig(this.entity);
        }
        else if (entityType == EntityType.PUFFERFISH) {
            return new DishPufferfish(this.entity);
        }
        else if (entityType == EntityType.RABBIT) {
            return new DishRabbit(this.entity);
        }
        else if (entityType == EntityType.SALMON) {
            return new DishSalmon(this.entity);
        }
        else if (entityType == EntityType.SHEEP) {
            return new DishSheep(this.entity);
        }
        else if (entityType == EntityType.SKELETON_HORSE) {
            return new DishSkeletonHorse(this.entity);
        }
        else if (entityType == EntityType.SNIFFER) {
            return new DishSniffer(this.entity);
        }
        else if (entityType == EntityType.SNOW_GOLEM) {
            return new DishSnowGolem(this.entity);
        }
        else if (entityType == EntityType.SQUID) {
            return new DishSquid(this.entity);
        }
        else if (entityType == EntityType.STRIDER) {
            return new DishStrider(this.entity);
        }
        else if (entityType == EntityType.TADPOLE) {
            return new DishTadpole(this.entity);
        }
        else if (entityType == EntityType.TROPICAL_FISH) {
            return new DishTropicalFish(this.entity);
        }
        else if (entityType == EntityType.TURTLE) {
            return new DishTurtle(this.entity);
        }
        else if (entityType == EntityType.VILLAGER) {
            return new DishVillager(this.entity);
        }
        else if (entityType == EntityType.WANDERING_TRADER) {
            return new DishWanderingTrader(this.entity);
        }
        // Neutral mobs
        else if (entityType == EntityType.BEE) {
            return new DishBee(this.entity);
        }
        else if (entityType == EntityType.CAVE_SPIDER) {
            return new DishCaveSpider(this.entity);
        }
        else if (entityType == EntityType.DOLPHIN) {
            return new DishDolphin(this.entity);
        }
        else if (entityType == EntityType.ENDERMAN) {
            return new DishEnderman(this.entity);
        }
        else if (entityType == EntityType.GOAT) {
            return new DishGoat(this.entity);
        }
        else if (entityType == EntityType.IRON_GOLEM) {
            return new DishIronGolem(this.entity);
        }
        else if (entityType == EntityType.LLAMA) {
            return new DishLlama(this.entity);
        }
        else if (entityType == EntityType.PANDA) {
            return new DishPanda(this.entity);
        }
        else if (entityType == EntityType.PIGLIN) {
            return new DishPiglin(this.entity);
        }
        else if (entityType == EntityType.POLAR_BEAR) {
            return new DishPolarBear(this.entity);
        }
        else if (entityType == EntityType.SPIDER) {
            return new DishSpider(this.entity);
        }
        else if (entityType == EntityType.TRADER_LLAMA) {
            return new DishTraderLlama(this.entity);
        }
        else if (entityType == EntityType.WOLF) {
            return new DishWolf(this.entity);
        }
        else if (entityType == EntityType.ZOMBIFIED_PIGLIN) {
            return new DishZombifiedPiglin(this.entity);
        }
        // Monster mobs
        else if (entityType == EntityType.BLAZE) {
            return new DishBlaze(this.entity);
        }
        else if (entityType == EntityType.CREEPER) {
            return new DishCreeper(this.entity);
        }
        else if (entityType == EntityType.DROWNED) {
            return new DishDrowned(this.entity);
        }
        else if (entityType == EntityType.ELDER_GUARDIAN) {
            return new DishElderGuardian(this.entity);
        }
        else if (entityType == EntityType.ENDERMITE) {
            return new DishEndermite(this.entity);
        }
        else if (entityType == EntityType.EVOKER) {
            return new DishEvoker(this.entity);
        }
        else if (entityType == EntityType.GHAST) {
            return new DishGhast(this.entity);
        }
        else if (entityType == EntityType.GUARDIAN) {
            return new DishGuardian(this.entity);
        }
        else if (entityType == EntityType.HOGLIN) {
            return new DishHoglin(this.entity);
        }
        else if (entityType == EntityType.HUSK) {
            return new DishHusk(this.entity);
        }
        else if (entityType == EntityType.MAGMA_CUBE) {
            return new DishMagmaCube(this.entity);
        }
        else if (entityType == EntityType.PHANTOM) {
            return new DishPhantom(this.entity);
        }
        else if (entityType == EntityType.PIGLIN_BRUTE) {
            return new DishPiglinBrute(this.entity);
        }
        else if (entityType == EntityType.PILLAGER) {
            return new DishPillager(this.entity);
        }
        else if (entityType == EntityType.RAVAGER) {
            return new DishRavager(this.entity);
        }
        else if (entityType == EntityType.SHULKER) {
            return new DishShulker(this.entity);
        }
        else if (entityType == EntityType.SILVERFISH) {
            return new DishSilverfish(this.entity);
        }
        else if (entityType == EntityType.SKELETON) {
            return new DishSkeleton(this.entity);
        }
        else if (entityType == EntityType.SLIME) {
            return new DishSlime(this.entity);
        }
        else if (entityType == EntityType.STRAY) {
            return new DishStray(this.entity);
        }
        else if (entityType == EntityType.VEX) {
            return new DishVex(this.entity);
        }
        else if (entityType == EntityType.VINDICATOR) {
            return new DishVindicator(this.entity);
        }
        else if (entityType == EntityType.WARDEN) {
            return new DishWarden(this.entity);
        }
        else if (entityType == EntityType.WITCH) {
            return new DishWitch(this.entity);
        }
        else if (entityType == EntityType.WITHER_SKELETON) {
            return new DishWitherSkeleton(this.entity);
        }
        else if (entityType == EntityType.ZOGLIN) {
            return new DishZoglin(this.entity);
        }
        else if (entityType == EntityType.ZOMBIE) {
            return new DishZombie(this.entity);
        }
        else if (entityType == EntityType.ZOMBIE_HORSE) {
            return new DishZombieHorse(this.entity);
        }
        else if (entityType == EntityType.ZOMBIE_VILLAGER) {
            return new DishZombieVillager(this.entity);
        }
        else if (entityType == EntityType.PLAYER) {
            return new DishPlayer((ServerPlayer)this.entity);
        }
        else if (entityType == EntityType.ENDER_DRAGON || entityType == EntityType.WITHER || entityType == EntityType.BREEZE) {
            return this;
        }
        else {
            return null;
        }
    }

    @Override
    @Nullable
    public EntityTypes getType() {
        if (this.entity == null) return null;
        return EntityTypes.valueOf(this.entity.getType().getDescription().getString().toUpperCase().replace(" ", "_"));
    }

    @Override
    public boolean isSilent() {
        return this.entity.isSilent();
    }

    @Override
    public void setSilent(boolean b) {
        this.entity.setSilent(b);
    }

    @Override
    public UUID getUUID() {
        return this.entity.getUUID();
    }

    @Override
    public float getHealth() {
        return this.entity.getHealth();
    }

    @Override
    public void setHealth(float health) {
        this.entity.setHealth(health);
    }
}
