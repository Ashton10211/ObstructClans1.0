package com.obstruct.clans.clans.events;

import com.obstruct.clans.clans.Clan;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ClanClaimEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean isCancelled;
    private final Chunk chunk;
    private final Clan clan;
    private boolean outline;
    private boolean message;

    public ClanClaimEvent(Player player, Chunk chunk, Clan clan) {
        super(player);
        this.chunk = chunk;
        this.clan = clan;
        this.outline = true;
        this.message = true;
    }

    public ClanClaimEvent(Player who, Chunk chunk, Clan clan, boolean outline, boolean message) {
        super(who);
        this.chunk = chunk;
        this.clan = clan;
        this.outline = outline;
        this.message = message;
    }


    public boolean isMessage() {
        return this.message;
    }


    public void setMessage(boolean message) {
        this.message = message;
    }


    public boolean isOutline() {
        return this.outline;
    }


    public void setOutline(boolean outline) {
        this.outline = outline;
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