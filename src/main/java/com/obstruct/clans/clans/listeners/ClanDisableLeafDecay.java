package com.obstruct.clans.clans.listeners;

import com.obstruct.clans.clans.ClanManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class ClanDisableLeafDecay extends SpigotModule<ClanManager> implements Listener {

    public ClanDisableLeafDecay(ClanManager manager) {
        super(manager, "ClanDisableLeafDecay");
    }

    @EventHandler
    public void stopLeafDecay(LeavesDecayEvent event) {
        if (getManager().getClan(event.getBlock().getLocation()) != null)
            event.setCancelled(true);
    }
}