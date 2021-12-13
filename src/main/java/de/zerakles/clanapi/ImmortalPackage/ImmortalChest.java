package de.zerakles.clanapi.ImmortalPackage;

import de.zerakles.clanapi.RuneSystem.Runes;
import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImmortalChest {


    public Runes rune;

    public ImmortalChest(Player player) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.LIGHT_PURPLE + "This is a Immortal Package, ");
        lore.add(ChatColor.LIGHT_PURPLE + "you have a chance to obtain special abilities ");
        UUID uuid = UUID.randomUUID();
        lore.add("§e" + uuid);
        ItemStack itemStack = new ItemStack(Material.ENDER_CHEST);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§cImmortal Packages";
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 1;

        rune = new Runes(displayName, lore, itemStack, (short) 0, dmg, uuid, player.getName());
    }
}