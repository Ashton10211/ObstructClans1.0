package com.obstruct.clans.clans.events;

import com.obstruct.clans.clans.Clan;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ClanMoveEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean isCancelled;

    private final Clan clanFrom;

    public ClanMoveEvent(Player player, Clan clanFrom, Clan clanTo, Location locFrom, Location locTo) {
        super(player);
        this.clanFrom = clanFrom;
        this.clanTo = clanTo;
        this.locFrom = locFrom;
        this.locTo = locTo;
    }

    private final Clan clanTo;
    private final Location locFrom;
    private final Location locTo;

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


    public Clan getClanFrom() {
        return this.clanFrom;
    }


    public Clan getClanTo() {
        return this.clanTo;
    }


    public Location getLocFrom() {
        return this.locFrom;
    }


    public Location getLocTo() {
        return this.locTo;
    }
}
