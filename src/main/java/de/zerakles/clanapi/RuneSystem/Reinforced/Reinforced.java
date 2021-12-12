package de.zerakles.clanapi.RuneSystem.Reinforced;

import de.zerakles.clanapi.RuneSystem.Runes;
import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class Reinforced {

    public Runes rune;

    public Reinforced(Player player) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(" ");
        lore.add("§aReinforced Rune");
        ItemStack itemStack = new ItemStack(Material.NETHER_STAR);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§6Ancient Rune";
        UUID uuid = UUID.randomUUID();
        lore.add("§e" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 1;

        rune = new Runes(displayName, lore, itemStack, (short) 0, dmg, uuid, player.getName());
    }
}

