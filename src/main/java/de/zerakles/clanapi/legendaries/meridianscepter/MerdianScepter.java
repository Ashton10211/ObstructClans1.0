package de.zerakles.clanapi.legendaries.meridianscepter;

import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class MerdianScepter {

    public Legend legend;

    public MerdianScepter(Player player) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 3);
        lore.add(" ");
        lore.add("§fLegend says that this scepter");
        lore.add("§fwas found, and retrieved from");
        lore.add("§fthe deepest trench in all of");
        lore.add("§fMinecraftia. It is said that he");
        lore.add("§fwields this scepter holds");
        lore.add("§fthe power of Poseidon himself.");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + player.getName());
        lore.add("§f");
        lore.add("§eRight-Click §f to use §aScepter.");
        ItemStack itemStack = new ItemStack(Material.RECORD_6);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lMeridian Scepter";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 3;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid, player.getName());
        Clan.getClan().meridianscepter.Scepter.put(player, legend);
    }
}
