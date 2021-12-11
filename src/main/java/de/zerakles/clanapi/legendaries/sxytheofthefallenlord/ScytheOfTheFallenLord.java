package de.zerakles.clanapi.legendaries.sxytheofthefallenlord;

import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class ScytheOfTheFallenLord {
    public Legend legend;
    public ScytheOfTheFallenLord(Player player) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 8);
        lore.add(" ");
        lore.add("§fAn old blade fashioned of nothing more");
        lore.add("§fthan bones and cloth which served no");
        lore.add("§fpurpose. Brave adventurers however have");
        lore.add("§fimbued it with the remnant powers of a");
        lore.add("§fdark and powerful foe.");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + player.getName());
        lore.add("§f");
        lore.add("§eAttack §f to use §aFly.");
        ItemStack itemStack = new ItemStack(Material.RECORD_8);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lScythe of the Fallen Lord";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 8;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid, player.getName());
        Clan.getClan().scytheOfTheFallenLordListener.ScytheOfs.put(legend, player);
    }
}
