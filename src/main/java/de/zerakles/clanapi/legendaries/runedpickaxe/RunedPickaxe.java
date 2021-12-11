package de.zerakles.clanapi.legendaries.runedpickaxe;

import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class RunedPickaxe {
    public Legend legend;
    public RunedPickaxe(Player player) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§fDamage: §e" + 3);
        lore.add(" ");
        lore.add("§fWhat an interesting design this");
        lore.add("§fpickaxe seems to have!");
        lore.add("§f");
        lore.add("§fOriginal Name: §e" + player.getName());
        lore.add("§f");
        lore.add("§eRight-Click §f to use §aInstant Mine.");
        ItemStack itemStack = new ItemStack(Material.RECORD_7);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = "§e§lRuned Pickaxe";
        UUID uuid = UUID.randomUUID();
        lore.add("§8" + uuid);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        int dmg = 3;
        legend = new Legend(displayName, lore, itemStack, (short) 0, dmg, uuid, player.getName());
        Clan.getClan().runedPickaxe.RunedPickaxes.put(legend, player);
    }
}
