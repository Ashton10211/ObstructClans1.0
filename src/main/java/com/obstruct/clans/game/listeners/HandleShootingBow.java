package com.obstruct.clans.game.listeners;

import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class HandleShootingBow extends SpigotModule<GameManager> implements Listener {

    public HandleShootingBow(GameManager manager) {
        super(manager, "HandleShootingBow");
    }

    @EventHandler
    public void onPlayerShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        if (getManager(ClanManager.class).getClan(player.getLocation()) != null && getManager(ClanManager.class).getClan(player.getLocation()).isAdmin() && getManager(ClanManager.class).getClan(player.getLocation()).isSafe(player.getLocation())) {
            event.setCancelled(true);
        }
    }
}
