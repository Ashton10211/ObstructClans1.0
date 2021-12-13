package de.zerakles.clanapi.RuneSystem.Reinforced;

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






@EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();


    if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
        if (player.getItemInHand().getItemMeta().getLore().contains("§aReinforced Rune")) {
            if (event.getClickedBlock().getType() == Material.STONE) {
                player.sendMessage("You have started Enchating with a Reinforced Rune, right click with a item you want to enchant");

                if (player.getItemInHand().getType() == Material.IRON_BOOTS) {
                    player.sendMessage("TESTING1");

                }
            }
        }
    }
}
}



