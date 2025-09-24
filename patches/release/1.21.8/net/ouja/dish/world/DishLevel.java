package net.ouja.dish.world;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.ouja.api.entity.Player;
import net.ouja.api.world.Level;
import net.ouja.dish.entity.DishPlayer;

import java.util.ArrayList;
import java.util.List;

public class DishLevel implements Level {
    private ServerLevel level;

    public DishLevel(ServerLevel level) {
        this.level = level;
    }

    @Override
    public String getName() {
        return this.level.dimensionType().effectsLocation().getPath();
    }

    @Override
    public long getSeed() {
        return this.level.getSeed();
    }

    @Override
    public List<Player> getPlayers() {
        List<Player> dishPlayers = new ArrayList<>(this.level.players().size());
        for (ServerPlayer player : this.level.players()) {
            dishPlayers.add(new DishPlayer(player));
        }
        return dishPlayers;
    }

    @Override
    public boolean isDay() {
        return this.level.dimensionType().hasSkyLight() && !this.level.isDarkOutside();
    }

    @Override
    public boolean isNight() {
        return this.level.dimensionType().hasSkyLight() && this.level.isDarkOutside();
    }

    @Override
    public long getGameTime() {
        return this.level.getGameTime();
    }

    @Override
    public long getDayTime() {
        return this.level.getDayTime();
    }

    @Override
    public DimensionType dimension() {
        if (this.level.dimension() == net.minecraft.world.level.Level.NETHER) return DimensionType.NETHER;
        if (this.level.dimension() == net.minecraft.world.level.Level.END) return DimensionType.END;
        return DimensionType.OVERWORLD;
    }
}
