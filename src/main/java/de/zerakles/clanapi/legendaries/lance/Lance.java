package de.zerakles.clanapi.legendaries.lance;

import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class Lance {
    public Legend legend;
    public Lance(Player player) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 7);
        lore.add(" ");
        lore.add("§fLong ago, a race of cloud dwellers");
        lore.add("§fterrorized the skies. A remnant of");
        lore.add("§ftheir tyranny, this airy blade is");
        lore.add("§fthe last surviving memorium from");
        lore.add("§ftheir final battle against the Titans.");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + player.getName());
        lore.add("§f");
        lore.add("§eRight-Click §f to use §aRun.");
        ItemStack itemStack = new ItemStack(Material.getMaterial(775));
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lLance";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 7;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid);
        Clan.getClan().lanceListener.Lances.put(player, legend);
    }
}
