package de.zerakles.listener;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TravelLIstener implements Listener {
    private Data getData(){
        return Clan.getClan().data;
    }

    private  Clan getClan(){
        return Clan.getClan();
    }

    private ClanAPI getClanAPI(){
        return getClan().getClanAPI();
    }

    final Location shopspawn = new Location(Bukkit.getWorld("world"), -624, 67, 309);

    @EventHandler
    public void onBankShop(InventoryClickEvent inventoryClickEvent)throws NullPointerException {
        if (inventoryClickEvent.getWhoClicked() instanceof Player) {
            if (inventoryClickEvent.getInventory().getTitle().equalsIgnoreCase("§8Travel")) {
                inventoryClickEvent.setCancelled(true);
                Player player = (Player) inventoryClickEvent.getWhoClicked();
                if (inventoryClickEvent.getCurrentItem().getType() == Material.AIR) {
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getType() == null) {
                    return;
                }
                if (inventoryClickEvent.getCurrentItem() == null
                        || inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName() == null
                        || inventoryClickEvent.getCurrentItem().getItemMeta() == null) {
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lSpawn")) {
                    player.closeInventory();
                    player.teleport(player.getWorld().getSpawnLocation());
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lHome")) {
                    player.closeInventory();
                    if(!getClanAPI().hashHome(player.getUniqueId().toString())){
                        player.sendMessage(getData().prefix + getData().noHomeRegistered);
                        return;
                    }
                    player.teleport(getClanAPI().getHome(player.getUniqueId().toString()));
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lShop")) {
                    player.closeInventory();
                    player.teleport(shopspawn);
                    return;
                }
            }
        }
    }
}
