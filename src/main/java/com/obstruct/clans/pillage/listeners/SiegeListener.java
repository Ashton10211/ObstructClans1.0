package com.obstruct.clans.pillage.listeners;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanRelation;
import com.obstruct.clans.pillage.Siege;
import com.obstruct.clans.pillage.SiegeManager;
import com.obstruct.clans.pillage.events.ClanSiegeEndEvent;
import com.obstruct.clans.pillage.events.ClanSiegeStartEvent;
import com.obstruct.core.shared.update.Update;
import com.obstruct.core.shared.update.Updater;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Iterator;

public class SiegeListener extends SpigotModule<SiegeManager> implements Listener, Updater {

    public SiegeListener(SiegeManager manager) {
        super(manager, "SiegeListener");
    }

    @Update
    public void onUpdate() {
        if (getManager().getSieges().isEmpty()) {
            return;
        }
        for (Iterator<Siege> it = getManager().getSieges().iterator(); it.hasNext(); ) {
            Siege siege = it.next();
            if (siege.getTimeRemaining() <= 0L) {
                it.remove();
                Bukkit.getServer().getPluginManager().callEvent(new ClanSiegeEndEvent(siege.getPillager(), siege.getPillagee()));
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSiegeStart(ClanSiegeStartEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Clan killer = event.getPillager();
        Clan dead = event.getPillagee();

        killer.getWarPoints().remove(dead.getName());
        dead.getWarPoints().remove(killer.getName());

        getManager().addSiege(new Siege(killer, dead, getManager().getSiegeTime()));
        ClanManager manager = getManager(ClanManager.class);
        for (Player online : Bukkit.getOnlinePlayers()) {
            ClanRelation clanRelation = manager.getClanRelation(killer, manager.getClan(online.getUniqueId()));
            ClanRelation deadRelation = manager.getClanRelation(dead, manager.getClan(online.getUniqueId()));
            UtilMessage.message(online, "War", clanRelation.getSuffix() + "Clan " + killer.getName() + ChatColor.GRAY + " can now beseige " + deadRelation.getSuffix() + "Clan " + dead.getName() + ChatColor.GRAY + ".");
        }
        getExecutorService().execute(() -> {
            getManager(ClanManager.class).saveClan(killer);
            getManager(ClanManager.class).saveClan(dead);
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPillageEnd(ClanSiegeEndEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Clan pillagee = event.getPillagee();
        Clan pillager = event.getPillager();
        pillagee.inform(true, "War", "The Siege on your Clan has finished!");
        pillager.inform(true, "War", "The Siege on " + ChatColor.YELLOW + "Clan " + pillagee.getName() + ChatColor.GRAY + " has finished!");
        pillagee.setSiegeCooldown(System.currentTimeMillis());
    }
}