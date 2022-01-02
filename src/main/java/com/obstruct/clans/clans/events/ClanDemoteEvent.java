package com.obstruct.clans.clans.events;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanMember;
import com.obstruct.core.shared.client.Client;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ClanDemoteEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean isCancelled;
    private final Clan clan;
    private final ClanMember target;

    public ClanDemoteEvent(Player player, ClanMember target, Clan clan) {
        super(player);
        this.target = target;
        this.clan = clan;
    }


    public ClanMember getTarget() {
        return this.target;
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
