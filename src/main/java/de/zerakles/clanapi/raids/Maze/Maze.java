package de.zerakles.clanapi.raids.Maze;

import net.minecraft.server.v1_8_R3.Block;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Maze implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
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
                }
            } else if (event.getClickedBlock().getType() != Material.LEVER) {
                return;

            }
        }
    }
}



