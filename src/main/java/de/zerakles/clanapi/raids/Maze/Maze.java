package de.zerakles.clanapi.raids.Maze;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class Maze implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location lever = event.getClickedBlock().getLocation();
        final Location lever1 = new Location(Bukkit.getWorld("world"), 35, 79, -180);
        final Location lever2 = new Location(Bukkit.getWorld("world"), 35, 79, -184);


        boolean level1 = false;
        boolean level2 = false;

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getClickedBlock().getType() == Material.LEVER) {
                if (lever.equals(lever1))
                    player.sendMessage("test1");
                else if (!lever.equals(lever1)) {
                    return;
                }
            }
        }
    }
}


