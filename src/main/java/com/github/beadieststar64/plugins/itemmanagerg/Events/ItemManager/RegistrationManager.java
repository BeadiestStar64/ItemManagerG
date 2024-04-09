package com.github.beadieststar64.plugins.itemmanagerg.Events.ItemManager;

import com.github.beadieststar64.plugins.itemmanagerg.Events.ItemManager.DataType.UUIDDataType;
import com.github.beadieststar64.plugins.itemmanagerg.Utils.YamlLoader;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class RegistrationManager implements Listener, Manageable {

    private final JavaPlugin plugin;
    private final YamlLoader messenger;

    private Material material;
    private String displayName;
    private Enchantment[] enchantments;
    private int[] enchantmentLevels;

    public RegistrationManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.messenger = new YamlLoader(plugin, "lang", new YamlLoader(plugin).getString("language") + ".yml");
    }

    @EventHandler
    public void onRegister(PlayerInteractEvent event) {
        YamlLoader recorder = new YamlLoader(plugin, "yml", "item.yml");
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            return;
        }
        if (!recorder.getList("items").contains(player.getInventory().getItemInMainHand())) {
            return;
        }
        NamespacedKey key = new NamespacedKey(plugin, "item-manager-g");
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (meta.getPersistentDataContainer().get(key, new UUIDDataType()).equals(player.getUniqueId())) {

        }
    }

    @Override
    public Component viewEntrance() {
        return Component.text("")
                .append(getMaterialComponent());
    }

    @Override
    public Component getMaterialComponent() {
        return Component
                .text(String.format("%s: %s",
                        messenger.getString("entrance.material"),
                        material != null ? material : messenger.getString("entrance.null")))
                .color(TextColor.color(255, 215, 0));
    }

    @Override
    public Component getItemNameComponent() {
        return null;
    }

    @Override
    public Component getEnchantmentComponent() {
        return null;
    }

    @Override
    public Component getEnchantmentLevelComponent() {
        return null;
    }
}
