package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilFormat;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class DisableBrewing extends SpigotModule<GameManager> implements Listener {
    public DisableBrewing(GameManager manager) {
        super(manager, "DisableBrewing");
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getPlayer() instanceof Player) {
            if (event.isCancelled()) {
                return;
            }
            Player player = (Player)event.getPlayer();
            if (event.getInventory().getType() == InventoryType.BREWING) {
                UtilMessage.message(player, "Game", ChatColor.YELLOW + UtilFormat.cleanString(event.getInventory().getType().toString()) + ChatColor.GRAY + " is disabled.");
                event.setCancelled(true);
            }
        }
    }
}
