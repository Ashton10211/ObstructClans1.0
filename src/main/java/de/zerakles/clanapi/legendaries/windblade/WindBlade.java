package de.zerakles.clanapi.legendaries.windblade;

import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class WindBlade {

    public Legend legend;
    public WindBlade(Player player) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 7);
        lore.add(" ");
        lore.add("§fLong ago, a race of cloud dwellers");
        lore.add("§fterrorized the skies. A remnant of!");
        lore.add("§ftheir tyranny, this airy blade is");
        lore.add("§fthe last surviving memorium from");
        lore.add("§ftheir final battle againts the Titans.");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + player.getName());
        lore.add("§f");
        lore.add("§eRight-Click §f to use §aFly.");
        ItemStack itemStack = new ItemStack(Material.GREEN_RECORD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lWindblade";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 7;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid, player.getName());
        Clan.getClan().windBladeListener.WindBlades.put(player, legend);
    }
}
