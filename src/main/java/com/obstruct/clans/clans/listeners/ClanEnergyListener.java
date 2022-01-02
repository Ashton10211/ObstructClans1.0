package com.obstruct.clans.clans.listeners;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanRelation;
import com.obstruct.clans.clans.events.ClanDisbandEvent;
import com.obstruct.clans.clans.events.ClanEnergyUpdateEvent;
import com.obstruct.core.shared.mongodb.MongoManager;
import com.obstruct.core.shared.update.Update;
import com.obstruct.core.shared.update.Updater;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Iterator;
import java.util.Map;

public class ClanEnergyListener extends SpigotModule<ClanManager> implements Updater {

    public ClanEnergyListener(ClanManager manager) {
        super(manager, "ClanEnergyListener");
    }

    @Update(ticks = 720000)
    public void hourlySave() {
        for (Clan clan : getManager().getClanMap().values()) {
            getManager(MongoManager.class).getDatastore().save(clan);
        }
    }

    @Update(ticks = 1200)
    public void onUpdate() {
        for (Map.Entry<String, Clan> stringClanEntry : getManager().getClanMap().entrySet()) {
            Clan clan = stringClanEntry.getValue();

            double energyFromHours = clan.getEnergyFromHours(1) / 60.0D;
            clan.setEnergy(clan.getEnergy() - (int) energyFromHours);
            if (clan.getEnergy() <= 0) {
                Bukkit.getPluginManager().callEvent(new ClanDisbandEvent(null, clan, ClanDisbandEvent.DisbandReason.ENERGY));
                continue;
            }
            Bukkit.getServer().getPluginManager().callEvent(new ClanEnergyUpdateEvent(clan));
        }
    }
}