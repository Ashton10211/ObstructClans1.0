package de.zerakles.clanapi.legendaries.magneticblade;

import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class MagneticMaul {
    public Legend legend;
    public MagneticMaul(Player player) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 8);
        lore.add(" ");
        lore.add("§fOf all the weapons known to man,");
        lore.add("§fnone is more magnetic than the");
        lore.add("§fMagnetic Maul. Infused with a high level");
        lore.add("§f of electricity even the strongest");
        lore.add("§fguy can be pulled...");
        lore.add(" ");
        lore.add("§eRight-Click §f to use §aPull.");
        ItemStack itemStack = new ItemStack(Material.RECORD_5);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lMagnetic Maul";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 8;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid);
        Clan.getClan().magneticMaul.MagneticMauls.put(player, legend);
    }
}
