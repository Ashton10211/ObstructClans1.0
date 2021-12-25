package com.obstruct.clans.clans.events;

import com.obstruct.clans.clans.Clan;
import com.obstruct.core.shared.client.Client;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ClanPromoteEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean isCancelled;
    private final Clan clan;
    private final Client target;

    public ClanPromoteEvent(Player player, Client target, Clan clan) {
        super(player);
        this.target = target;
        this.clan = clan;
    }


    public Client getTarget() { return this.target; }



    public Clan getClan() { return this.clan; }



    public static HandlerList getHandlerList() { return handlers; }




    public HandlerList getHandlers() { return handlers; }




    public boolean isCancelled() { return this.isCancelled; }




    public void setCancelled(boolean b) { this.isCancelled = b; }
}
