package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilFormat;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class DisableSandGravel extends SpigotModule<GameManager> implements Listener {

    public DisableSandGravel(GameManager manager) {
        super(manager, "DisableSandGravel");
    }

    @EventHandler
    public void onDisableSandGravel(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.SAND || event.getBlock().getType() == Material.GRAVEL) {
            event.setCancelled(true);
            UtilMessage.message(event.getPlayer(), "Game", "You cannot place " + ChatColor.YELLOW + UtilFormat.cleanString(event.getBlock().getType().name()) + ChatColor.GRAY + ".");
        }
    }
}
