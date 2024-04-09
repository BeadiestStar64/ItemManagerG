package com.github.beadieststar64.plugins.itemmanagerg.Events.ItemManager;

import net.kyori.adventure.text.Component;

public interface Manageable {

    Component viewEntrance();
    Component getMaterialComponent();
    Component getItemNameComponent();
    Component getEnchantmentComponent();
    Component getEnchantmentLevelComponent();
}
