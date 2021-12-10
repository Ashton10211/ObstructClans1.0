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
        final Location lever1 = new Location(Bukkit.getWorld("world"), 22, 78, -178);
        final Location lever2 = new Location(Bukkit.getWorld("world"), 22, 78, -178);


        boolean level1 = false;
        boolean level2 = false;

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getClickedBlock().getType() == Material.LEVER) {
                if (lever.equals(lever1))
                    player.sendMessage("test1");
                level1 = true;
                player.sendMessage("player.sendMessage(ChatColor.BLUE + \"Guardian Of The Sea Messenger> \" + ChatColor.YELLOW + \"You have found a lever, you are now one step closer to completing the Mariana Trench.");
                if (lever.equals(lever2)) {
                    player.sendMessage("player.sendMessage(ChatColor.BLUE + \"Guardian Of The Sea Messenger> \" + ChatColor.YELLOW + \"You have found a lever, you are now one step closer to completing the Mariana Trench.");
                    level2 = true;
                    if (level1 && level2) ;
                    player.sendMessage(ChatColor.BLUE + "Guardian Of The Sea Messenger> " + ChatColor.YELLOW + "Congratulations you have finished the Mariana Trench. Teleporting you to the next level.");
                    level1 = false;
                    level2 = false;
                }
            } else if (event.getClickedBlock().getType() != Material.LEVER) {
                return;

            }
        }
    }
}



