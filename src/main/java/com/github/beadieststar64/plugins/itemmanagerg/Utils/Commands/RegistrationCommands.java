package com.github.beadieststar64.plugins.itemmanagerg.Utils.Commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.management.Attribute;

public class RegistrationCommands extends MasterCommands {
    public RegistrationCommands(JavaPlugin plugin) {
        super(plugin);
    }

    private Material material;
    private String displayName;
    private Enchantment[] enchantments;
    private int[] enchantmentLevels;
    private Attribute[] attributes;
    private AttributeModifier.Operation[] operations;

    public boolean registration(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            String type = sender instanceof ConsoleCommandSender ? messenger.getString("console.type.sender.console") : messenger.getString("console.type.sender.command-block");
            sender.sendMessage(
                    Component.text(messenger.getString("console.error.deny-permission-sender-type")
                                    .replaceAll("-SENDER-", type))
                            .color(TextColor.color(255, 0, 0))
            );
            return true;
        }
        Player player = (Player) sender;
        if (player.hasPermission(config.getString("permission.admin.all"))) {
            player.sendMessage("OK!");
            return true;
        }
        return false;
    }
}
