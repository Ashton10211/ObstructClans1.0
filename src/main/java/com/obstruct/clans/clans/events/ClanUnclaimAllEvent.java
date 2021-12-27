package com.obstruct.clans.clans.events;

import com.obstruct.clans.clans.Clan;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ClanUnclaimAllEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean isCancelled;
    private final Clan clan;

    public ClanUnclaimAllEvent(Player who, Clan clan) {
        super(who);
        this.clan = clan;
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
