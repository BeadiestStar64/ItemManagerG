package com.github.beadieststar64.plugins.itemmanagerg;

import com.github.beadieststar64.plugins.itemmanagerg.Events.ChestShop.CreateShop;
import com.github.beadieststar64.plugins.itemmanagerg.Utils.Commands.MasterCommands;
import com.github.beadieststar64.plugins.itemmanagerg.Utils.FileManager;
import com.github.beadieststar64.plugins.itemmanagerg.Utils.YamlLoader;
import org.bukkit.command.PluginCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class ItemManagerG extends JavaPlugin {

    private static final List<String> softDepends = new ArrayList<>();

    public static List<String> getSoftDepends() {return softDepends;}
    public static void addSoftDepends(String plugin) {softDepends.add(plugin);}

    @Override
    public void onEnable() {
        // Initialization step
        FileManager manager = new FileManager(this);
        manager.initialize("RequestFiles.txt");

        // Identify soft dependencies
        checkSoftDepend();

        // Register commands
        registrationCommands(new String[]{
                "manager"
        });

        // Register events
        registrationEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registrationEvents() {
        getServer().getPluginManager().registerEvents(new CreateShop(this), this);
    }

    private void registrationCommands(String[] commands) {
        for (String rawCommand : commands) {
            PluginCommand command = getCommand(rawCommand);
            boolean bool = command != null;
            if (bool) {
                command.setExecutor(new MasterCommands(this));
                command.setTabCompleter(new MasterCommands(this));
            }
        }
    }

    private void checkSoftDepend() {
        YamlLoader config = new YamlLoader(this);
        YamlLoader messenger = new YamlLoader(this, "lang", config.getString("language") + ".yml");
        List<?> list = config.getList("soft-depends");
        for (Object str : list) {
           if (getServer().getPluginManager().getPlugin((String) str) == null && config.getBoolean("use-" + ((String) str).toLowerCase())) {
               getLogger().warning(messenger.getString("console.info.soft-depend-information").replaceAll("-PLUGIN-", (String) str));
           }
        }
    }
}
