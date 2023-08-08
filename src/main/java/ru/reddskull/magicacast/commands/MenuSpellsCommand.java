package ru.reddskull.magicacast.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MenuSpellsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) return true;
        if (args.length != 0) return false;

        Player player = (Player) commandSender;

        Inventory spellMenu = Bukkit.createInventory(null, 9, "SpellMenu"); //1 строки

        ItemStack spellHeal = new ItemStack(Material.POTION);
        ItemStack spellInvisible = new ItemStack(Material.SUGAR);

        ItemMeta sHealMeta = spellHeal.getItemMeta();  //sHealMeta = spellHealMeta
        sHealMeta.setDisplayName(ChatColor.YELLOW + "Заклинание " + ChatColor.DARK_RED + "'Востановление'");
        ArrayList<String> sHealLore = new ArrayList<>();
        sHealLore.add(ChatColor.WHITE + "Зелье которое восcтанавливает не которую часть ваше здоровье!");
        sHealMeta.setLore(sHealLore);
        spellHeal.setItemMeta(sHealMeta);

        ItemMeta sInvMeta = spellInvisible.getItemMeta();  //sInvMeta = spellInvisibleMeta
        sInvMeta.setDisplayName(ChatColor.YELLOW + "Порошок " + ChatColor.GRAY + "'Исчезн" + ChatColor.WHITE + "овение'");
        ArrayList<String> sInvLore = new ArrayList<>();
        sInvLore.add(ChatColor.WHITE + "С помощью магического порошка вы сможите стать невидимым!");
        sInvMeta.setLore(sInvLore);
        spellInvisible.setItemMeta(sInvMeta);

        ItemStack[] item = {spellHeal, spellInvisible};
        spellMenu.setContents(item);
        player.openInventory(spellMenu);
        return true;

    }
}
