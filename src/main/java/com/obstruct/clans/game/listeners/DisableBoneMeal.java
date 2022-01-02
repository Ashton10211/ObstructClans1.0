package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class DisableBoneMeal extends SpigotModule<GameManager> implements Listener {

    public DisableBoneMeal(GameManager manager) {
        super(manager, "DisableBoneMeal");
    }

    @EventHandler
    public void onBoneMeal(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && player.getItemInHand().getType() == Material.INK_SACK && player.getItemInHand().getData().getData() == 15) {
            UtilMessage.message(player, "Game", ChatColor.YELLOW + "Bone Meal" + ChatColor.GRAY + " has been disabled.");
            event.setCancelled(true);
        }
    }
}