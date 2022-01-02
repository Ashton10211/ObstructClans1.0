package com.obstruct.clans.game.listeners;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class DisableCreatureSpawn extends SpigotModule<GameManager> implements Listener {

    public DisableCreatureSpawn(GameManager manager) {
        super(manager, "DisableCreatureSpawn");
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        Clan clan = getManager(ClanManager.class).getClan(event.getEntity().getLocation().getChunk());
        if (clan != null && clan.isAdmin() && event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL)
            event.setCancelled(true);
    }
}