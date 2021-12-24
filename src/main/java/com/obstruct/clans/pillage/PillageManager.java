package com.obstruct.clans.pillage;

import com.obstruct.clans.clans.Clan;
import com.obstruct.core.spigot.framework.SpigotBasePlugin;
import com.obstruct.core.spigot.framework.SpigotManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class PillageManager extends SpigotManager<SpigotModule<?>> {

    private final Set<Pillage> pillages = new HashSet<>();

    public PillageManager(SpigotBasePlugin plugin) {
        super(plugin, "PillageManager");
    }

    @Override
    public void registerModules() {

    }

    public void addPillage(Pillage pillage) {
        this.pillages.add(pillage);
    }

    public boolean isPillaging(Clan clan, Clan target) {
        if(clan == null || target == null) {
            return false;
        }
        for (Pillage pillage : getPillages()) {
            if(pillage.getPillager().equals(clan) && pillage.getPillagee().equals(target)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPillaged(Clan clan, Clan target) {
        if(clan == null || target == null) {
            return false;
        }
        for (Pillage pillage : getPillages()) {
            if(pillage.getPillager().equals(target) && pillage.getPillagee().equals(clan)) {
                return true;
            }
        }
        return false;
    }

    public boolean isGettingPillaged(Clan clan) {
        if (clan == null) {
            return false;
        }
        for (Pillage pillage : getPillages()) {
            if(pillage.getPillagee().equals(clan)) {
                return true;
            }
        }
        return false;
    }
}