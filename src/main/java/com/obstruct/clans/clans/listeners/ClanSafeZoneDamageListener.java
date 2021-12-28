package com.obstruct.clans.clans.listeners;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.core.spigot.combat.event.CustomDamageEvent;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ClanSafeZoneDamageListener extends SpigotModule<ClanManager> implements Listener {

    public ClanSafeZoneDamageListener(ClanManager manager) {
        super(manager, "ClanSafeZoneDamageListener");
    }

    @EventHandler
    public void onSafeZoneDamage(CustomDamageEvent event) {
        Player player = event.getDamageePlayer();
        if (player == null) {
            return;
        }
        Clan clan = getManager().getClan(player.getLocation());
        if (clan == null) {
            return;
        }
        if (clan.isAdmin() && clan.isSafe() && (event.getDamageCause() == EntityDamageEvent.DamageCause.DROWNING || event.getDamageCause() == EntityDamageEvent.DamageCause.FALL))
            event.setCancelled(true);
    }
}