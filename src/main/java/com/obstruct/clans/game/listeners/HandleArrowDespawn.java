package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class HandleArrowDespawn extends SpigotModule<GameManager> implements Listener {

    public HandleArrowDespawn(GameManager manager) {
        super(manager, "HandleArrowDespawn");
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof org.bukkit.entity.Arrow)
            event.getEntity().remove();
    }
}
