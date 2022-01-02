package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

public class WebBreak extends SpigotModule<GameManager> implements Listener {

    public WebBreak(GameManager manager) {
        super(manager, "WebBreak");
    }

    @EventHandler
    public void onWebBreak(BlockDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getBlock().getType() == Material.WEB)
            event.setInstaBreak(true);
    }
}
