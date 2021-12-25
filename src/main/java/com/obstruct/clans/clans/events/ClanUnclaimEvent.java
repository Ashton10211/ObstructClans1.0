package com.obstruct.clans.clans.events;

import com.obstruct.clans.clans.Clan;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ClanUnclaimEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean isCancelled;
    private final Chunk chunk;
    private final Clan clan;

    public ClanUnclaimEvent(Player player, Clan clan) {
        super(player);
        this.chunk = player.getLocation().getChunk();
        this.clan = clan;
    }


    public Chunk getChunk() {
        return this.chunk;
    }


    public Clan getClan() {
        return this.clan;
    }


    public static HandlerList getHandlerList() {
        return handlers;
    }


    public HandlerList getHandlers() {
        return handlers;
    }


    public boolean isCancelled() {
        return this.isCancelled;
    }


    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }
}
