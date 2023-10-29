package net.ouja.dish.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.ouja.api.Player;
import net.ouja.api.world.Level;
import net.ouja.dish.network.chat.DishComponent;
import net.ouja.dish.world.DishLevel;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class DishPlayer implements Player {
    private net.minecraft.world.entity.player.Player player;
    public static HashMap<UUID, DishPlayer> CACHED_PLAYERS = new HashMap<>();

    public DishPlayer(ServerPlayer player) {
        this.player = player;
        if (player != null) {
            CACHED_PLAYERS.putIfAbsent(player.getUUID(), this);
        }
    }

    @Override
    public @NotNull String getName() {
        return player.getName().getString();
    }

    @Override
    public @NotNull UUID getUUID() {
        return player.getUUID();
    }

    @Override
    public Level getLevel() {
        if (isConsole()) return null;
        return new DishLevel(player.getServer().getLevel(player.getCommandSenderWorld().dimension()));
    }

    @Override
    public boolean isConsole() {
        return this.player == null;
    }

    @Override
    public void sendMessage(net.ouja.api.network.chat.Component component) {
//        this.player.sendSystemMessage(Component.literal(message));
        this.player.sendSystemMessage(new DishComponent(component));
    }
}
