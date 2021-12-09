package de.zerakles.clanapi.legendaries.giantbroadsword;

import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class GiantBroadSword {
    public Legend legend;

    public GiantBroadSword(Player player) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 10);
        lore.add(" ");
        lore.add("§fForged in the godly mined of Plagieus");
        lore.add("§fthis sword has endured thousands of");
        lore.add("§fwars. It is sure to grant certain");
        lore.add("§fvictory in battle..");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + player.getName());
        lore.add("§f");
        lore.add("§eRight-Click §f to use §ashield.");
        ItemStack itemStack = new ItemStack(Material.GOLD_RECORD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lGiant Broadsword";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 10;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid);
        Clan.getClan().giantbroadswordListener.GiantBroadSwords.put(player, legend);
    }
}
