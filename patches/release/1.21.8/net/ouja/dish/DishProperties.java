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
        return this.properties.onlineMode;
    }

    @Override
    public boolean isHardcore() {
        return this.properties.hardcore;
    }

    @Override
    public @NotNull String getMotd() {
        return this.properties.motd;
    }

    @Override
    public boolean isPvPEnabled() {
        return this.properties.pvp;
    }

    @Override
    public boolean isFlightEnabled() {
        return this.properties.allowFlight;
    }

    @Override
    public @Nullable Boolean isAnnouncePlayerAchievementsEnabled() {
        return this.properties.announcePlayerAchievements;
    }

    @Override
    public boolean isNetherAllowed() {
        return this.properties.allowNether;
    }

    @Override
    public boolean canSpawnMonsters() {
        return this.properties.spawnMonsters;
    }

    @Override
    public int getSpawnProtection() {
        return this.properties.spawnProtection;
    }

    @Override
    public int getViewDistance() {
        return this.properties.viewDistance;
    }

    @Override
    public int getSimulationDistance() {
        return this.properties.simulationDistance;
    }

    @Override
    public int getMaxPlayers() {
        return this.properties.maxPlayers;
    }

    @Override
    public boolean hideOnlinePlayers() {
        return this.properties.hideOnlinePlayers;
    }

    @Override
    public boolean isWhitelistEnabled() {
        return this.properties.enforceWhitelist;
    }

    @Override
    public boolean acceptsServerTransfers() {
        return this.properties.acceptsTransfers;
    }
}
