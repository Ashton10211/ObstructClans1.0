package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class DisableBlockIgnite extends SpigotModule<GameManager> implements Listener {

    public DisableBlockIgnite(GameManager manager) {
        super(manager, "DisableBlockIgnite");
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if(event.getCause() != BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) {
            event.setCancelled(true);
        }
    }
}
