package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class HandleBlockPlaceHeight extends SpigotModule<GameManager> implements Listener {

    public HandleBlockPlaceHeight(GameManager manager) {
        super(manager, "HandleBlockPlaceHeight");
    }

    @EventHandler
    public void onCancelBlock(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getBlock().getLocation().getY() > 150.0D) {
            UtilMessage.message(event.getPlayer(), "Restriction", "You can only place blocks lower than 150Y!");
            event.setCancelled(true);
        }
    }
}
