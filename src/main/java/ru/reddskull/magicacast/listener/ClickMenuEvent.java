package ru.reddskull.magicacast.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.reddskull.magicacast.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class ClickMenuEvent implements Listener {

    ArrayList<Player> invisibleList = new ArrayList<>();

    Main plugin;

    public ClickMenuEvent(Main plugin) {
        this.plugin = plugin;
    }

    private final HashMap<UUID, Long> cooldown;

    public ClickMenuEvent(){
        this.cooldown = new HashMap<>();
    }

    @EventHandler
    public void clickEvent(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equalsIgnoreCase("SpellMenu")) {

            switch (e.getCurrentItem().getType()) {
                case POTION:
                    player.closeInventory();

                    if (!player.hasPermission("magicacast.heal")) {
                        player.sendMessage(ChatColor.RED + "Нет знаний для использования заклинания.");
                        return;
                    }

                    if (this.cooldown.containsKey(player.getUniqueId())) {
                        this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());

                        double maxheal = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                        player.setHealth(maxheal);
                        player.sendMessage(ChatColor.GREEN + "Здоровье восстановлено!");

                        player.spawnParticle(Particle.HEART, player.getLocation(), 3);

                        if (!e.isCancelled()) ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE, 1, 1);
                        if (!e.isCancelled()) ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_AMETHYST_BLOCK_BREAK, 1, 1);
                    }else{

                        long timeElap = System.currentTimeMillis() - cooldown.get(player.getUniqueId());

                        if (timeElap > 60000){
                            this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                        }else{
                            player.sendMessage(ChatColor.RED + "Перезарядка заклинания не закончилась!");
                        }
                    }
                    break;

                case SUGAR:
                    player.closeInventory();

                    if (!player.hasPermission("magicacast.Invisible")) {
                        player.sendMessage(ChatColor.RED + "Нет знаний для использования магического порошка.");
                        return;
                    }

                    if (invisibleList.contains(player)) {
                        for (Player people : Bukkit.getOnlinePlayers()) {
                            people.showPlayer(plugin, player);
                            player.sendMessage(ChatColor.RED + "Вы теперь видимы!");
                        }
                        invisibleList.remove(player);

                    } else if (invisibleList.contains(player)) {
                        for (Player people : Bukkit.getOnlinePlayers()) {
                            people.hidePlayer(plugin, player);
                            player.sendMessage(ChatColor.GREEN + "Теперь вы в невидимости!");
                        }
                        invisibleList.add(player);

                        if (!e.isCancelled()) ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 2);
                        if (!e.isCancelled()) ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 1);

                        break;
                    }

                    e.setCancelled(true);
            }
        }
    }
}