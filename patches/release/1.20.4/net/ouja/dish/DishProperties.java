package net.ouja.dish;

import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.ouja.api.dedicated.ServerProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DishProperties implements ServerProperties {
    private final DedicatedServerProperties properties;

    public DishProperties(DedicatedServerProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean isOnline() {
        return properties.onlineMode;
    }

    @Override
    public boolean isHardcore() {
        return properties.hardcore;
    }

    @Override
    public @NotNull String getMotd() {
        return properties.motd;
    }

    @Override
    public boolean canSpawnAnimals() {
        return properties.spawnAnimals;
    }

    @Override
    public boolean canSpawnNpcs() {
        return properties.spawnNpcs;
    }

    @Override
    public boolean isPvPEnabled() {
        return properties.pvp;
    }

    @Override
    public boolean isFlightEnabled() {
        return properties.allowFlight;
    }

    @Override
    public @Nullable Boolean isAnnouncePlayerAchievementsEnabled() {
        return properties.announcePlayerAchievements;
    }

    @Override
    public boolean isNetherAllowed() {
        return properties.allowNether;
    }

    @Override
    public boolean canSpawnMonsters() {
        return properties.spawnMonsters;
    }

    @Override
    public int getSpawnProtection() {
        return properties.spawnProtection;
    }

    @Override
    public int getViewDistance() {
        return properties.viewDistance;
    }

    @Override
    public int getSimulationDistance() {
        return properties.simulationDistance;
    }

    @Override
    public int getMaxPlayers() {
        return properties.maxPlayers;
    }

    @Override
    public boolean hideOnlinePlayers() {
        return properties.hideOnlinePlayers;
    }

    @Override
    public boolean isWhitelistEnabled() {
        return properties.enforceWhitelist;
    }
}
