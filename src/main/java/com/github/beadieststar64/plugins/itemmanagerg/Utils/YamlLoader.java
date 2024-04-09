package com.github.beadieststar64.plugins.itemmanagerg.Utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class YamlLoader {

    private FileConfiguration yaml = null;
    private final File yamlFile;
    private final String yamlString;
    private final JavaPlugin plugin;

    public YamlLoader(JavaPlugin plugin) {
        this(plugin, "");
    }

    public YamlLoader(JavaPlugin plugin, String folderName) {
        this(plugin, folderName, "config.yml");
    }

    public YamlLoader(JavaPlugin plugin, String folderName, String fileName) {
        this.plugin = plugin;
        this.yamlString = fileName;
        if (folderName.isEmpty()) {
            yamlFile = new File(plugin.getDataFolder(), fileName);
            saveDefault(fileName);
        } else {
            yamlFile = new File(plugin.getDataFolder() + File.separator + folderName, fileName);
            saveDefault(new File(plugin.getDataFolder(), folderName), fileName);
        }
    }

    public void saveDefault(String fileName) {
        if (!yamlFile.exists()) {
            FileManager manager = new FileManager(plugin);
            manager.createFile(plugin.getDataFolder(), fileName);
        }
    }

    public void saveDefault(File folder, String fileName) {
        if (!yamlFile.exists()) {
            FileManager manager = new FileManager(plugin);
            manager.createFile(folder, fileName);
        }
    }

    public void reloadYaml() {
        yaml = YamlConfiguration.loadConfiguration(yamlFile);
        final InputStream defYamlStream = plugin.getResource(yamlString);
        if(defYamlStream == null) {
            return;
        }
        yaml.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defYamlStream, StandardCharsets.UTF_8)));
    }

    public FileConfiguration getYaml() {
        if(yaml == null) {
            reloadYaml();
        }
        return yaml;
    }

    public void saveConfig() {
        if (yaml == null) {
            return;
        }
        try {
            getYaml().save(yamlFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + yamlFile, ex);
        }
        reloadYaml();
    }

    public String getString(String key) {
        return getYaml().getString(key);
    }

    public List<?> getList(String key) {
        return getYaml().getList(key);
    }
    public boolean getBoolean(String key) {return getYaml().getBoolean(key);}
}
