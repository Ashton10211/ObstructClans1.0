package com.obstruct.clans.pillage.listeners;

import com.obstruct.clans.pillage.Siege;
import com.obstruct.clans.pillage.SiegeManager;
import com.obstruct.core.shared.update.Update;
import com.obstruct.core.shared.update.Updater;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilTime;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

public class SiegeAnnouncer extends SpigotModule<SiegeManager> implements Updater {

    public SiegeAnnouncer(SiegeManager manager) {
        super(manager, "SiegeAnnouncer");
    }

    @Update(ticks = 0)
    public void onUpdate() {
        if(getManager().getSieges().isEmpty()) {
            return;
        }
        for (Siege siege : getManager().getSieges()) {
            if(UtilTime.elapsed(siege.getLastAnnounce(), 60000L)) {
                String remainingString = UtilTime.getTime2(siege.getTimeRemaining(), UtilTime.TimeUnit.MINUTES, 1);

                siege.getPillagee().inform(true, "War", "The Siege on your clan ends in " + ChatColor.GREEN + remainingString + ChatColor.GRAY + ".");
                siege.getPillager().inform(true, "War", "The Siege on " + ChatColor.LIGHT_PURPLE + "Clan " + siege.getPillagee().getName() + ChatColor.GRAY + " ends in " + ChatColor.GREEN + remainingString + ChatColor.GRAY + ".");

                siege.setLastAnnounce(System.currentTimeMillis());

                siege.getPillagee().playSound(Sound.NOTE_PLING, 0.5F, 1.2F);
                siege.getPillager().playSound(Sound.NOTE_PLING, 0.5F, 1.2F);
            }
        }
    }
}