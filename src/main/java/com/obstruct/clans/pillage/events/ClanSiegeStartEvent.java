package com.obstruct.clans.pillage.events;

import com.obstruct.clans.clans.Clan;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClanSiegeStartEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean isCancelled;
    private final Clan pillager;
    private final Clan pillagee;

    public ClanSiegeStartEvent(Clan pillager, Clan pillagee) {
        this.pillager = pillager;
        this.pillagee = pillagee;
    }


    public Clan getPillagee() {
        return this.pillagee;
    }


    public Clan getPillager() {
        return this.pillager;
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
