package net.ouja.dish.plugins;

import net.ouja.api.plugin.JavaPlugin;
import net.ouja.api.plugin.Plugin;
import net.ouja.dish.Dish;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager {
    private int MAX_CONFIG_VERSION = 1;

    public static ArrayList<String> plugins = new ArrayList<>();

    public PluginManager(File pluginsDir) throws IOException {
        for (File file : pluginsDir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".jar")) {
                JarFile jar = new JarFile(file.getAbsolutePath());
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().equalsIgnoreCase("dish.yml") || entry.getName().equalsIgnoreCase("dish.yaml")) {
                        InputStream is = jar.getInputStream(entry);
                        Map<String, Object> data = readConfigurationFile(is);
                        boolean isValid = isConfigValid(data, file.getName());
                        if (!isValid) {
                            Dish.logger.error(String.format("[Dish Plugin Manager '%s'] is not valid, skipping...", file.getName()));
                            continue;
                        }
                        try {
                            ClassLoader loader = URLClassLoader.newInstance(
                                    new URL[]{file.getAbsoluteFile().toURI().toURL()},
                                    getClass().getClassLoader()
                            );
                            Class jarClass = Class.forName(data.get("main-class").toString(), true, loader);
                            Class<? extends JavaPlugin> pluginClass;
                            try {
                                pluginClass = jarClass.asSubclass(JavaPlugin.class);
                            } catch (ClassCastException e) {
                                throw new ClassCastException("main class for plugin_name could not extend to JavaPlugin");
                            }
                            Plugin plugin = ((Plugin)pluginClass.newInstance());
                            plugin.onEnable();
                            plugins.add(file.getName());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (InstantiationException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    public Map<String, Object> readConfigurationFile(InputStream is) {
        Yaml yaml = new Yaml();
        return yaml.load(is);
    }

    public boolean isConfigValid(Map<String, Object> data, String pluginName) {
        try {
            if (data.get("config-version").toString() != null && (Integer.parseInt(data.get("config-version").toString()) > MAX_CONFIG_VERSION || Integer.parseInt(data.get("config-version").toString()) < 0)) {
                Dish.logger.error(String.format("[Dish Plugin Manager '%s'] Invalid config-version used in dish.yaml", pluginName));
                return false;
            }

            if (Integer.parseInt(data.get("config-version").toString()) == 1) {
                // used if needed
            }

            if (data.get("main-class") == null) {
                Dish.logger.error(String.format("[Dish Plugin Manager '%s'] Missing main-class in dish.yaml", pluginName));
                return false;
            }

            if (data.get("id") == null) {
                Dish.logger.error(String.format("[Dish Plugin Manager '%s'] Missing id in dish.yaml", pluginName));
                return false;
            }

            return true;
        } catch (Exception e) {
            Dish.logger.error(String.format("[Dish Plugin Manager '%s'] Invalid dish.yaml\n%s", pluginName, e.getMessage()));
            return false;
        }
    }
}
