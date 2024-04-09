package com.github.beadieststar64.plugins.itemmanagerg.Utils.Commands;

import com.github.beadieststar64.plugins.itemmanagerg.Utils.YamlLoader;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MasterCommands implements TabExecutor {

    protected YamlLoader messenger;
    protected YamlLoader config;
    protected JavaPlugin plugin;

    public MasterCommands(JavaPlugin plugin) {
        this.plugin = plugin;
        this.config = new YamlLoader(plugin);
        this.messenger = new YamlLoader(plugin, "lang", config.getString("language") + ".yml");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (command.getName().equalsIgnoreCase("manager")) {
            DisplayHelp help = new DisplayHelp(plugin);
            if (strings.length == 0) {
               commandSender.sendMessage(
                       Component.text()
                               .append(help::getVersionInfo)
                               .append(help::getHelpRegister)
                               .append(help::getHelpWikiInfo)
               );
               return true;
            } else {
                switch (strings[0].toLowerCase()) {
                    case "register" -> {
                        RegistrationCommands register = new RegistrationCommands(plugin);
                        return register.registration(commandSender, strings);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> args;
        List<String> result = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("manager")) {
            args = new ArrayList<>(Arrays.asList(
                    "register",
                    "edit",
                    "display",
                    "remove",
                    "help"
            ));
            if (strings.length == 1) {
                for (String option : args) {
                    if (option.toLowerCase().startsWith(strings[0].toLowerCase())) {
                        result.add(option);
                    }
                }
                Collections.sort(result);
                return result;
            }
        }
        return null;
    }
}
