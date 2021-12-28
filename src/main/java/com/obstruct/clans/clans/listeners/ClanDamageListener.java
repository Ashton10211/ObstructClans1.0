package com.obstruct.clans.clans.listeners;

import com.obstruct.clans.clans.ClanManager;
import com.obstruct.core.spigot.combat.event.CustomDamageEvent;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ClanDamageListener extends SpigotModule<ClanManager> implements Listener {

    public ClanDamageListener(ClanManager manager) {
        super(manager, "ClanDamageListener");
    }

    @EventHandler
    public void onCustomDamage(CustomDamageEvent event) {
        Player damagee = event.getDamageePlayer();
        Player damager = event.getDamagerPlayer();
        if(damagee == null || damager == null) {
            return;
        }
        if(!getManager().canHurt(damager, damagee, true)) {
            event.setCancelled(true);
        }
    }
}