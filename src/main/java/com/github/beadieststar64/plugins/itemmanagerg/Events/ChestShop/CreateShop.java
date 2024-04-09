package com.github.beadieststar64.plugins.itemmanagerg.Events.ChestShop;

import com.Acrobot.ChestShop.Events.PreShopCreationEvent;
import com.github.beadieststar64.plugins.itemmanagerg.Utils.YamlLoader;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CreateShop implements Listener {

    private final JavaPlugin plugin;

    public CreateShop(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCreate(PreShopCreationEvent event) {
        Player player = event.getPlayer();
        if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            return;
        }
        YamlLoader recorder = new YamlLoader(plugin, "", "item.yml");
        List<?> list = recorder.getList("items");
        if (!list.contains(player.getInventory().getItemInMainHand().getType())) {
            return;
        }
    }
}
