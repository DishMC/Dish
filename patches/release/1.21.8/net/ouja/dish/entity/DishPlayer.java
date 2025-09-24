package net.ouja.dish.entity;

import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.ouja.api.entity.Player;
import net.ouja.api.world.Level;
import net.ouja.api.world.level.block.BlockPos;
import net.ouja.dish.network.chat.DishComponent;
import net.ouja.dish.server.level.DishClientInformation;
import net.ouja.dish.world.DishLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class DishPlayer extends DishEntity implements Player {
    private net.minecraft.server.level.ServerPlayer player;
    public static HashMap<UUID, DishPlayer> CACHED_PLAYERS = new HashMap<>();

    public DishPlayer(ServerPlayer player) {
        super(player);
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
        return new DishLevel(player.getServer().getLevel(player.level().dimension()));
    }

    @Override
    public boolean isConsole() {
        return this.player == null;
    }

    @Override
    public void sendMessage(net.ouja.api.network.chat.Component component) {
        this.player.sendSystemMessage(new DishComponent(component));
    }

    @Nullable
    @Override
    public BlockPos lastDeathLocation() {
        GlobalPos globalPos = this.player.getLastDeathLocation().get();
        if (globalPos == null) return null;
        return new BlockPos(globalPos.pos().getX(), globalPos.pos().getX(), globalPos.pos().getX(), new DishLevel((ServerLevel)this.player.level()));
    }

    @Override
    public double getReach() {
        return this.player.blockInteractionRange();
    }

    @Override
    public float getScale() {
        return this.player.getScale();
    }

    @Override
    public void setReach(double reach) {
        if (reach >= 0) {
            this.player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE).setBaseValue(reach);
        }
    }

    @Override
    public void setScale(float scale) {
        if (scale >= 0) {
            this.player.getAttribute(Attributes.SCALE).setBaseValue(scale);
        }
    }

    @NotNull
    @Override
    public DishClientInformation getClientInformation() {
        ClientInformation info = this.player.clientInformation();
        return new DishClientInformation(info);
    }
}
