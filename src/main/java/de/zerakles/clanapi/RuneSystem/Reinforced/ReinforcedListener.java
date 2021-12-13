package de.zerakles.clanapi.RuneSystem.Reinforced;

import de.zerakles.utils.UtilItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ReinforcedListener implements Listener {


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        ItemStack item = event.getCurrentItem();
        if (item == null) {
            return;
        }
        if (!(UtilItem.isArmour(item.getType()))) {
            return;
        }
        if (!(item.hasItemMeta())) {
            return;
        }
        if (!(item.getItemMeta().getLore().get(0).contains("Reinforced Rune"))) {
            return;
        }


        if(!(ChatColor.stripColor(item.getItemMeta().getLore().get(0)).contains("Reinforced Rune"))) {
            return;
        }


        Player player = (Player) event.getWhoClicked();

        player.sendMessage("Works!");
    }



    @EventHandler
    public void onDamage(EntityDamageEvent entityDamageEvent) {
        if (entityDamageEvent.getEntity() instanceof Player) {
            Player player = (Player) entityDamageEvent.getEntity();
            if (player.getInventory().getBoots() != null) {
                ItemStack itemStack = player.getInventory().getBoots();
                if (itemStack.hasItemMeta()) {
                    if (itemStack.getItemMeta().hasLore()) {
                        if (itemStack.getItemMeta().getLore().contains("§aReinforced -0.5")) {
                            player.sendMessage("Reinforced is working");

                        }
                    }
                }
            }
        }
    }
}









