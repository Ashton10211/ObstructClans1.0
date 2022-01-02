package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.utility.UtilItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class HandleItemNameChange extends SpigotModule<GameManager> implements Listener {

    public HandleItemNameChange(GameManager manager) {
        super(manager, "HandleItemNameChange");
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        UtilItem.updateNames(e.getItem().getItemStack());
    }
}