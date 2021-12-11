package de.zerakles.clanapi.quests;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;


public class QuestListener implements Listener {

    private Data getData() {
        return Clan.getClan().data;
    }

    private Clan getClan() {
        return Clan.getClan();
    }

    private ClanAPI getClanAPI() {
        return getClan().getClanAPI();
    }

    @EventHandler
    public void onQuestManager(InventoryClickEvent inventoryClickEvent) throws NullPointerException {
        if (inventoryClickEvent.getWhoClicked() instanceof Player) {
            if (inventoryClickEvent.getInventory().getTitle().equalsIgnoreCase("§8Quest Manager")) {
                inventoryClickEvent.setCancelled(true);
                Player player = (Player) inventoryClickEvent.getWhoClicked();
                if (inventoryClickEvent.getCurrentItem() == null) {
                    return;
                }

                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lDaily Quests")) {
                    for (ItemStack contents : player.getInventory().getContents()) {
                        if (contents != null) {
                            if (contents.getType() == Material.EMERALD) {
                                int am = contents.getAmount();
                                if (am > 1) {
                                    am = am - 1;
                                    contents.setAmount(am);
                                } else {
                                    player.getInventory().remove(contents);
                                }
                                getClanAPI().updateGold(player, getClanAPI().getGold(player) + 16);
                                player.sendMessage(getData().prefix + getData().addGold.replaceAll("%gold%", "16"));
                                return;
                            }
                        }
                    }
                    return;
                }
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lCash In Gold Token")) {
                    for (ItemStack contents : player.getInventory().getContents()) {
                        if (contents != null) {
                            if (contents.getType() == Material.RABBIT_FOOT) {
                                int am = contents.getAmount();
                                if (am > 1) {
                                    am = am - 1;
                                    contents.setAmount(am);
                                } else {
                                    player.getInventory().remove(contents);
                                }
                                getClanAPI().updateGold(player, getClanAPI().getGold(player) + 50000);
                                player.sendMessage(getData().prefix + getData().addGold.replaceAll("%gold%", "50000"));
                                return;
                            }
                        }
                    }
                    return;
                }
                return;
            }
            return;
        }
        return;
    }
}