package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;

public class DisableBlockBurn extends SpigotModule<GameManager> implements Listener {

    public DisableBlockBurn(GameManager manager) {
        super(manager, "DisableBlockBurn");
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) { event.setCancelled(true); }
}