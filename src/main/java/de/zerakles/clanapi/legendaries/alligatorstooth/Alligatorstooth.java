package de.zerakles.clanapi.legendaries.alligatorstooth;

import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class Alligatorstooth {

    public Legend legend;

    public Alligatorstooth(Player player) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 7);
        lore.add(" ");
        lore.add("§fThis deadly tooth was stolen from");
        lore.add("§fa best of reptillian beasts long");
        lore.add("§fago. Legends say that the holder");
        lore.add("§fis granted the underwater agility");
        lore.add("§fof an Alligator.");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + player.getName());
        lore.add("§f");
        lore.add("§eRight-Click §f to use §aswim.");
        ItemStack itemStack = new ItemStack(Material.RECORD_4);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lAlligators Tooth";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 7;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid, player.getName());
        Clan.getClan().alligatorsToothListener.AlligatorThooths.put(legend, player);
    }
}
