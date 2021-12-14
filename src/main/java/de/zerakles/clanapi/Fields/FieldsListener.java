package de.zerakles.clanapi.Fields;

import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

import static de.zerakles.main.Clan.getClan;

public class FieldsListener implements Listener {


    private Data getData(){
        return Clan.getClan().data;
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = (Player) e.getPlayer();
        Block b = (Block) e.getBlock();


        Location fields = player.getLocation();
        getData().Shop = fields;
        if (b.getType() == Material.IRON_ORE) {
            player.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
            e.setCancelled(true);
            b.setType(Material.STONE);
            player.sendMessage(ChatColor.BLUE + "Fields> " + ChatColor.YELLOW + "You have mined 1 Iron Ore");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getClan(), () -> {
                    b.setType(Material.IRON_ORE);
            },1200L);
            if (b.getType() == Material.GOLD_ORE) {
                player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                e.setCancelled(true);
                b.setType(Material.STONE);
                player.sendMessage(ChatColor.BLUE + "Fields> " + ChatColor.YELLOW + "You have mined 1 Gold Ore");
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getClan(), () -> {
                    b.setType(Material.GOLD_INGOT);
                },1200L);
                if (!(b.getType() == Material.IRON_ORE)) {
                    return;
                }
            }
        }
    }
}