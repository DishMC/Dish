package net.ouja.dish.commands;

import net.minecraft.commands.Commands;
import net.ouja.dish.Dish;
import net.ouja.dish.entity.DishPlayer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class RegisteredCommands {
    private static final HashMap<String, Method> names = new HashMap<>();
    private static final HashMap<String, Class<?>> classes = new HashMap<>();

    public RegisteredCommands(String name, Method method, Class<?> commandClass) {
        if (names.get(name) != null) {
            Dish.logger.warn(String.format("[Warning] Command name '%s' already exists. Changing name to '%s%s'%n", name, name, names.size() + 1));
            name = name + (names.size() + 1);
        }

        names.put(name, method);
        classes.put(name, commandClass);

        String finalName = name;
        Commands.getDispatcher().register(Commands.literal(name).executes((cmd) -> {
            return runCommand(finalName, new DishPlayer(cmd.getSource().getPlayer())) ? 1 : 0;
        }));
    }

    public static boolean runCommand(String commandName, DishPlayer player) {
        Method method = names.get(commandName);
        Class<?> clazz = classes.get(commandName);
        if (method == null || clazz == null) return false;

        try {
            method.setAccessible(true);
            return (boolean)method.invoke(clazz.newInstance(), player);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return false;
    }
}
