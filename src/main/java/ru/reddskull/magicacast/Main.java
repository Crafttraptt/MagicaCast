package ru.reddskull.magicacast;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Main extends JavaPlugin {
    public static Main plugin;
    // эту штуку надо ВСЕГДА ставить
    public static final Logger log = Logger.getLogger("Minecraft");
    // штука на уменьшение лога

    @Override
    public void onEnable() {
        plugin = this;

        log.info("MagicaCast включён!");
        // хрень на лог в консоле
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
