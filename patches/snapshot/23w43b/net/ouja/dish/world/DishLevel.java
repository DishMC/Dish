package net.ouja.dish.world;

import net.minecraft.server.level.ServerLevel;
import net.ouja.api.Player;
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
        return new ArrayList<>(DishPlayer.CACHED_PLAYERS.values());
    }
}
