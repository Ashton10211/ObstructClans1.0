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
        lore.add("§fRelic of a bygone age.");
        lore.add("§fEmblazoned with cryptic runes, this");
        lore.add("§fLance bears the marks of its ancient master.");
        lore.add("§fYou feel him with you always:");
        lore.add("§fMeed his warnings and stave off the darkness.");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + player.getName());
        lore.add("§f");
        lore.add("§eRight-Click §f to use §aCharge.");
        ItemStack itemStack = new ItemStack(Material.RECORD_12);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lKnight's Greatlance";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 7;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid, player.getName());
        Clan.getClan().lanceListener.Lances.put(player, legend);
    }
}
