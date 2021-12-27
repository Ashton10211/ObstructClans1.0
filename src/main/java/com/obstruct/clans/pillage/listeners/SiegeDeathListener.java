package com.obstruct.clans.pillage.listeners;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanRelation;
import com.obstruct.clans.pillage.SiegeManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilTime;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SiegeDeathListener extends SpigotModule<SiegeManager> implements Listener {

    public SiegeDeathListener(SiegeManager manager) {
        super(manager, "SiegeDeathListener");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        Player player = event.getEntity();
        Clan clan = getManager(ClanManager.class).getClan(player.getUniqueId());
        if(clan == null) {
            return;
        }
        Player killer = player.getKiller();
        if(killer == null) {
            return;
        }
        Clan killerClan = getManager(ClanManager.class).getClan(killer.getUniqueId());
        if(killerClan == null) {
            return;
        }
        ClanRelation clanRelation = getManager(ClanManager.class).getClanRelation(clan, killerClan);
        if(UtilTime.elapsed(clan.getSiegeCooldown(), 1800000L)) {
            killerClan.inform(true, "Clans", "Your Clan did not gain a War Point because " + clanRelation.getSuffix() + "Clan " + clan.getName() + ChatColor.GRAY + " has been Sieged recently.");
            return;
        }
        getManager().giveWarPoint(killerClan, clan, 1);
    }
}
