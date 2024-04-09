package com.github.beadieststar64.plugins.itemmanagerg.Utils.Commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.plugin.java.JavaPlugin;

public class DisplayHelp extends MasterCommands {

    private final String format = "- %s: %s\n";

    public DisplayHelp(JavaPlugin plugin) {
        super(plugin);
    }

    public Component getVersionInfo() {
        return Component
                .text("ItemManager G Edition v." + plugin.getPluginMeta().getVersion() + "\n")
                .color(TextColor.color(0, 169, 104))
                .decoration(TextDecoration.BOLD, true);
    }

    public Component getHelpWikiInfo() {
        return Component
                .text(String.format(format,
                        "wiki",
                        messenger.getString("commands.command-list.help.wiki"))
                );
    }

    public Component getHelpRegister() {
        return Component
                .text(String.format(format,
                        "register",
                        messenger.getString("commands.command-list.help.registration"))
                );
    }

    public Component getWiki() {
        return Component
                .text(messenger.getString("commands.command-list.wiki") + "\n")
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL,plugin.getPluginMeta().getWebsite() + "/wiki")
                );
    }
}
