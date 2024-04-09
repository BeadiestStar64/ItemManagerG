package com.github.beadieststar64.plugins.itemmanagerg.Events.ManagerEvents;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class RecordRegistrationEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private boolean isCanceled;
    private Player registrant;
    private ItemStack registeredItem;
    private NamespacedKey itemKey;

    public RecordRegistrationEvent(@NotNull Player registrant, @NotNull ItemStack registeredItem, @NotNull NamespacedKey itemKey) {
        this.registrant = registrant;
        this.registeredItem = registeredItem;
        this.itemKey = itemKey;
        this.isCanceled = false;
    }

    @Override
    public boolean isCancelled() {
        return isCanceled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCanceled = b;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public Player getPlayer() {
        return this.registrant;
    }

    public void setPlayer(Player registrant) {
        this.registrant = registrant;
    }

    public ItemStack getItemStack() {
        return registeredItem == null ? getPlayer().getInventory().getItemInMainHand() : registeredItem;
    }

    public void setItemStack(ItemStack registeredItem) {
        this.registeredItem = registeredItem;
    }

    public NamespacedKey getNamespacedKey() {
        return this.itemKey;
    }

    public void setNamespacedKey(NamespacedKey itemKey) {
        this.itemKey = itemKey;
    }
}
