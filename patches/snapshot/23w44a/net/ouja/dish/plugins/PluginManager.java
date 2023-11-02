package net.ouja.dish.plugins;

import net.ouja.api.plugin.JavaPlugin;
import net.ouja.api.plugin.Plugin;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager {
    private Plugin plugin;

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
                            plugin = pluginClass.newInstance();
                            plugin.onEnable();
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

    public Plugin getPlugin() {
        return plugin;
    }

    public Map<String, Object> readConfigurationFile(InputStream is) {
        Yaml yaml = new Yaml();
        return yaml.load(is);
    }
}
