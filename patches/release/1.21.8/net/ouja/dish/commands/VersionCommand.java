package net.ouja.dish.commands;

import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.ouja.api.DishAPI;
import net.ouja.dish.Dish;

public class VersionCommand {
    public static void register() {
        Commands.getDispatcher().register(Commands.literal("version").executes((commandcontext) -> {
            commandcontext.getSource().sendSuccess(() -> {
                return Component.literal(String.format("DishServer version is '%s' implementing API '%s'", Dish.getBuild(), DishAPI.getApiVersion()));
            }, false);

            return 1;
        }));
    }
}
