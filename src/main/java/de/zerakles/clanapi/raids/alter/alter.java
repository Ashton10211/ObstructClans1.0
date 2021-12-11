package de.zerakles.clanapi.raids.alter;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class alter implements Listener {



    String ShopName = "§c§lGuardian Of The Sea Messenger";

    @EventHandler(priority= EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location raidteleporter  = event.getClickedBlock().getLocation();


        final Location raidteleporter1 = new Location(Bukkit.getWorld("world"), 22, 78, -178);


        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(raidteleporter.equals(raidteleporter1))
            if (player.getItemInHand().getType() == Material.IRON_INGOT) {
                if(event.getClickedBlock().getType() == Material.PRISMARINE) {

                    player.sendMessage(ChatColor.BLUE + "Guardian Of The Sea Messenger> " + ChatColor.YELLOW + "You have summoned the Guardian Of The Sea \n" + ChatColor.GRAY + "Be wise on the decisions you make further on.");
                    final org.bukkit.Location location = new Location(Bukkit.getWorld("world"), 32, 76, -182);
                    player.teleport(location);
                }
            }

            if (!raidteleporter.equals(raidteleporter1));
            {
            }
            if (event.getClickedBlock().getType() != Material.PRISMARINE) {
                return;
            }

            else if (player.getItemInHand().getType() != Material.IRON_INGOT) {
                player.sendMessage(ChatColor.BLUE + "Guardian Of The Sea Messenger> " + ChatColor.YELLOW + "You don't have the right materials to summon the Guardian of The Sea");
            }
        }
    }}



