package net.ouja.dish;

import net.ouja.api.DishAPI;
import net.ouja.dish.plugins.PluginManager;
import net.ouja.dish.plugins.RegisteredEvents;

import java.util.function.Supplier;

public class DishCrashReport implements Supplier<String> {
    @Override
    public String get() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\t\tVersion: " + Dish.getBuild());
        stringBuilder.append("\n\t\tAPI Version: " + DishAPI.getApiVersion());
        stringBuilder.append("\n\t\tLoaded Plugins: " + PluginManager.plugins.size());
        if (!PluginManager.plugins.isEmpty()) {
            stringBuilder.append("\n\t\tPlugins:");
            for (String pluginName : PluginManager.plugins) {
                stringBuilder.append("\n\t\t\t" + pluginName);
            }
        }
        stringBuilder.append("\n\t\tLoaded Events: " + RegisteredEvents.listeners.size());
        if (!RegisteredEvents.listeners.isEmpty()) {
            stringBuilder.append("\n\t\tEvents:");
            // EventType; pluginName.jar
            for (int i = 0; i < RegisteredEvents.listeners.size(); i++) {
                if (i < 99) {
                    String fileName = RegisteredEvents.classes.get(i).getProtectionDomain().getCodeSource().getLocation().getFile().split("/plugins/")[1];
                    stringBuilder.append("\n\t\t\t" + RegisteredEvents.listeners.get(i).getName() + "; " + fileName);
                } else {
                    stringBuilder.append("\n\t\t\tTruncated loaded events.");
                    break;
                }
            }
        }

        return stringBuilder.toString();
    }
}
