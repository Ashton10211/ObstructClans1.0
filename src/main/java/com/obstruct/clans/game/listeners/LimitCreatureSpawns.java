package com.obstruct.clans.game.listeners;

import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilMath;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class LimitCreatureSpawns extends SpigotModule<GameManager> implements Listener {

    public LimitCreatureSpawns(GameManager manager) {
        super(manager, "LimitCreatureSpawns");
    }

    @EventHandler
    public void limitSpawns(CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) {
            return;
        }
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING) {
            e.setCancelled(true);
            return;
        }
        if (e.getEntity().getCustomName() != null) {
            return;
        }
        if (e.getEntity().getType() == EntityType.ARMOR_STAND) {
            return;
        }
        if (getManager(ClanManager.class).getClan(e.getLocation()) != null) {
            e.setCancelled(true);
            return;
        }
        if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL) {
            return;
        }
        if (UtilMath.randomInt(10) <= 5)
            e.setCancelled(true);
    }
}
