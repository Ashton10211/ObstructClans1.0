package de.zerakles.clanapi.legendaries.hyperaxe;

import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class HyperAxe {

    public Legend legend;

    public HyperAxe(Player player) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 6);
        lore.add(" ");
        lore.add("§fOf all the weapons known to man,");
        lore.add("§fnone is more prevalant than the");
        lore.add("§fHyper Axe. Infused with rabbit's");
        lore.add("§fspeed and pigman's ferocity, this");
        lore.add("§fblade can rip through any opponent.");
        lore.add("§Hit delay is reduced by§e 50%");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + player.getName());
        lore.add("§f");
        lore.add("§eRight-Click §f to use §aDash.");
        ItemStack itemStack = new ItemStack(Material.RECORD_3);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lHyperAxe";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 6;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid, player.getName());
        Clan.getClan().hyperAxeListener.HyperAxes.put(player, legend);
    }
}
