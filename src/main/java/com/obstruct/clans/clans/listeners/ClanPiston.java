package com.obstruct.clans.clans.listeners;

import com.obstruct.clans.clans.ClanManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

public class ClanPiston extends SpigotModule<ClanManager> implements Listener {

    public ClanPiston(ClanManager manager) {
        super(manager, "ClanPiston");
    }

    @EventHandler
    public void onPistonEvent(BlockPistonRetractEvent e) {
        for (Block b : e.getBlocks()) {
            if ((getManager().getClan(b.getLocation().getChunk()) != null && getManager().getClan(e.getBlock().getLocation().getChunk()) == null) || getManager().getClan(e.getBlock().getLocation().getChunk()) != getManager().getClan(b.getLocation().getChunk())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPistonEvent(BlockPistonExtendEvent e) {
        for (Block b : e.getBlocks()) {
            if ((getManager().getClan(b.getLocation().getChunk()) != null && getManager().getClan(e.getBlock().getLocation().getChunk()) == null) || getManager().getClan(e.getBlock().getLocation().getChunk()) != getManager().getClan(b.getLocation().getChunk()))
                e.setCancelled(true);
        }
    }
}