package com.obstruct.clans.pillage;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanRelation;
import com.obstruct.clans.pillage.events.ClanSiegeStartEvent;
import com.obstruct.clans.pillage.listeners.SiegeAnnouncer;
import com.obstruct.clans.pillage.listeners.SiegeDeathListener;
import com.obstruct.clans.pillage.listeners.SiegeListener;
import com.obstruct.core.shared.annotations.SaveAndLoad;
import com.obstruct.core.spigot.framework.SpigotBasePlugin;
import com.obstruct.core.spigot.framework.SpigotManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;

@Getter
public class SiegeManager extends SpigotManager<SpigotModule<?>> {

    @SaveAndLoad
    private final long siegeTime = 1800000L;

    private final Set<Siege> sieges = new HashSet<>();

    public SiegeManager(SpigotBasePlugin plugin) {
        super(plugin, "SiegeManager");
    }

    @Override
    public void registerModules() {
        addModule(new SiegeDeathListener(this));
        addModule(new SiegeAnnouncer(this));
        addModule(new SiegeListener(this));
    }

    public void giveWarPoint(Clan killer, Clan dead, int points) {
        if (!killer.getWarPoints().containsKey(dead.getName())) {
            killer.getWarPoints().put(dead.getName(), 0);
        }
        if (!dead.getWarPoints().containsKey(killer.getName())) {
            dead.getWarPoints().put(killer.getName(), 0);
        }
        ClanRelation clanRelation = getManager(ClanManager.class).getClanRelation(killer, dead);
        if (dead.getWarPoints().get(killer.getName()) <= killer.getWarPoints().get(dead.getName())) {
            killer.getWarPoints().put(dead.getName(), killer.getWarPoints().get(dead.getName()) + points);
            killer.inform(true, "Clans", "Your Clan gained " + points + " War Point against " + clanRelation.getSuffix() + "Clan " + dead.getName() + ChatColor.WHITE + " (+" + killer.getWarPoints(dead) + ")");
            dead.inform(true, "Clans", "Your Clan lost " + points + " War Point against " + clanRelation.getSuffix() + "Clan " + killer.getName() + ChatColor.WHITE + " (-" + killer.getWarPoints(dead) + ")");
        } else if (dead.getWarPoints().get(killer.getName()) > killer.getWarPoints().get(dead.getName())) {
            dead.getWarPoints().put(killer.getName(), dead.getWarPoints().get(killer.getName()) - points);
            dead.inform(true, "Clans", "Your Clan lost " + points + " War Point against " + clanRelation.getSuffix() + "Clan " + killer.getName() + ChatColor.WHITE + " (+" + dead.getWarPoints(killer) + ")");
            killer.inform(true, "Clans", "Your Clan gained " + points + " War Point against " + clanRelation.getSuffix() + "Clan " + killer.getName() + ChatColor.WHITE + " (-" + dead.getWarPoints(killer) + ")");
        }
        getExecutorService().execute(() -> {
            getManager(ClanManager.class).saveClan(killer);
            getManager(ClanManager.class).saveClan(dead);
        });
        if (killer.getWarPoints(dead) >= 25) {
            Bukkit.getPluginManager().callEvent(new ClanSiegeStartEvent(killer, dead));
        }
    }

    public void addSiege(Siege pillage) {
        this.sieges.add(pillage);
    }

    public boolean isSieging(Clan clan, Clan target) {
        if (clan == null || target == null) {
            return false;
        }
        for (Siege pillage : getSieges()) {
            if (pillage.getPillager().equals(clan) && pillage.getPillagee().equals(target)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSieged(Clan clan, Clan target) {
        if (clan == null || target == null) {
            return false;
        }
        for (Siege pillage : getSieges()) {
            if (pillage.getPillager().equals(target) && pillage.getPillagee().equals(clan)) {
                return true;
            }
        }
        return false;
    }

    public boolean isGettingSieged(Clan clan) {
        if (clan == null) {
            return false;
        }
        for (Siege pillage : getSieges()) {
            if (pillage.getPillagee().equals(clan)) {
                return true;
            }
        }
        return false;
    }
}