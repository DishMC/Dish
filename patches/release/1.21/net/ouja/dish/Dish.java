package net.ouja.dish;

import com.mojang.logging.LogUtils;
import net.minecraft.server.ServerLinks;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.players.UserBanListEntry;
import net.ouja.api.DishAPI;
import net.ouja.api.entity.Player;
import net.ouja.api.Server;
import net.ouja.api.auth.GameProfile;
import net.ouja.api.commands.Command;
import net.ouja.api.commands.CommandListener;
import net.ouja.api.dedicated.ServerProperties;
import net.ouja.api.event.EventHandler;
import net.ouja.api.event.EventListener;
import net.ouja.api.network.chat.Component;
import net.ouja.api.server.ServerLink;
import net.ouja.api.server.players.BanEntry;
import net.ouja.dish.commands.RegisteredCommands;
import net.ouja.dish.commands.VersionCommand;
import net.ouja.dish.entity.DishPlayer;
import net.ouja.dish.network.chat.DishComponent;
import net.ouja.dish.plugins.PluginManager;
import net.ouja.dish.plugins.RegisteredEvents;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Dish implements Server {
    private final DedicatedServer server;
    private final String dishVersion = getBuild();
    public static final Logger logger = LogUtils.getLogger();

    public Dish(DedicatedServer server) {
        this.server = server;
        DishAPI.setServer(this);
        logger.info(String.format("Launching Dish server with the version %s (API Version: %s)", dishVersion, DishAPI.getApiVersion()));
        initializeDishCommands();
    }

    public static String getBuild() {
        try {
            InputStream in = Dish.class.getResourceAsStream("/build.txt");
            if (in == null) return "UNKNOWN";

            return readLines(in)[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "UNKNOWN";
    }

    public static String[] readLines(InputStream is) throws IOException {
        List<String> list = new ArrayList<>();
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.US_ASCII);
        BufferedReader br = new BufferedReader(isr);

        while(true) {
            String line = br.readLine();
            if (line == null) {
                return list.toArray(new String[0]);
            }

            list.add(line);
        }
    }

    public void initializePlugins() {
        try {
            new PluginManager(new File("plugins"));
        } catch (IOException e) {
            e.printStackTrace(); // todo make an error handler
        }
    }

    public void initializeDishCommands() {
        VersionCommand.register();
    }

    @Override
    public @NotNull String getVersion() {
        return this.server.getServerVersion();
    }

    @Override
    public @NotNull ServerProperties getProperties() {
        return new DishProperties(this.server.getProperties());
    }

    @Override
    public @NotNull String getDishVersion() {
        return this.dishVersion;
    }

    @Override
    public @NotNull ArrayList<Player> getOnlinePlayers() {
        return new ArrayList<>(DishPlayer.CACHED_PLAYERS.values());
    }

    @Override
    public void registerEvent(EventListener eventListener, Class<?> aClass) {
        for (Method method : eventListener.getClass().getMethods()) {
            if (method.isAnnotationPresent(EventHandler.class)) {
                new RegisteredEvents(method, aClass);
            }
        }
    }

    @Override
    public void registerCommand(Command command) {
        for (Method method : command.getClass().getMethods()) {
            if (method.isAnnotationPresent(CommandListener.class)) {
                CommandListener commandListener = method.getAnnotation(CommandListener.class);
                if (commandListener != null && commandListener.name() != "") {
                    new RegisteredCommands(commandListener, method, command.getClass());
                }
            }
        }
    }

    @Override
    public void broadcast(Component component) {
        for (Player player : DishPlayer.CACHED_PLAYERS.values()) {
            player.sendMessage(component);
        }
    }

    @Override
    public boolean isPlayerBanned(GameProfile profile) {
        return this.server.getPlayerList().getBans().isBanned(new com.mojang.authlib.GameProfile(profile.getPlayerUUID(), profile.getPlayerName()));
    }

    @Override
    public BanEntry getBanEntry(GameProfile profile) {
        UserBanListEntry entry = this.server.getPlayerList().getBans().get(new com.mojang.authlib.GameProfile(profile.getPlayerUUID(), profile.getPlayerName()));
        if (entry == null) return null;
        return new BanEntry(entry.getCreated(), entry.getSource(), entry.getExpires(), entry.getReason());
    }

    @Override
    public ArrayList<ServerLink> getServerLinks() {
        var list = new ArrayList<ServerLink>();

        for (ServerLinks.Entry entry : this.server.serverLinks().entries()) {
            list.add(new ServerLink(DishComponent.fromComponent(entry.displayName()), entry.link()));
        }

        return list;
    }
}
