package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class DisableBeacon extends SpigotModule<GameManager> implements Listener {

    public DisableBeacon(GameManager manager) {
        super(manager, "DisableBeacon");
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getPlayer() instanceof org.bukkit.entity.Player) {
            if (event.isCancelled()) {
                return;
            }
            if (event.getInventory().getType() == InventoryType.BEACON)
                event.setCancelled(true);
        }
    }
}