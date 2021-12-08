package de.zerakles.listener;

import de.zerakles.main.Clan;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SoupListener implements Listener {

    @EventHandler
    public void onSoup(FoodLevelChangeEvent foodLevelChangeEvent){
        if(foodLevelChangeEvent.getEntity().getItemInHand().getType() == Material.MUSHROOM_SOUP){
            foodLevelChangeEvent.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent playerInteractEvent){
        if(playerInteractEvent.getPlayer().getItemInHand().getType() == Material.MUSHROOM_SOUP) {
            if (playerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR
                    || playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if(playerInteractEvent.getPlayer().getItemInHand().getAmount() == 1){
                    playerInteractEvent.getPlayer().getInventory().remove(playerInteractEvent.getPlayer().getItemInHand());
                } else {
                    playerInteractEvent.getPlayer().getItemInHand().setAmount(playerInteractEvent.getPlayer().getItemInHand().getAmount()-1);
                }
                playerInteractEvent.setCancelled(true);
                if(playerInteractEvent.getPlayer().getHealth() == 20){
                    return;
                }
                Bukkit.getScheduler().scheduleAsyncDelayedTask(Clan.getClan(), new Runnable() {
                    @Override
                    public void run() {
                        if(playerInteractEvent.getPlayer().getHealth() != 20){
                            playerInteractEvent.getPlayer().setHealth(playerInteractEvent.getPlayer().getHealth()+1);
                        }
                    }
                },20);
                Bukkit.getScheduler().scheduleAsyncDelayedTask(Clan.getClan(), new Runnable() {
                    @Override
                    public void run() {
                        if(playerInteractEvent.getPlayer().getHealth() != 20){
                            playerInteractEvent.getPlayer().setHealth(playerInteractEvent.getPlayer().getHealth()+1);
                        }
                    }
                },40);
                Bukkit.getScheduler().scheduleAsyncDelayedTask(Clan.getClan(), new Runnable() {
                    @Override
                    public void run() {
                        if(playerInteractEvent.getPlayer().getHealth() != 20){
                            playerInteractEvent.getPlayer().setHealth(playerInteractEvent.getPlayer().getHealth()+1);
                        }
                    }
                },60);
                Bukkit.getScheduler().scheduleAsyncDelayedTask(Clan.getClan(), new Runnable() {
                    @Override
                    public void run() {
                        if(playerInteractEvent.getPlayer().getHealth() != 20){
                            playerInteractEvent.getPlayer().setHealth(playerInteractEvent.getPlayer().getHealth()+1);
                        }
                    }
                },80);
            }
        }
    }

}
