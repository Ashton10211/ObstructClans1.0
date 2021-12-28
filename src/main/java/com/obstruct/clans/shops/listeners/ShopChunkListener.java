package com.obstruct.clans.shops.listeners;

import com.obstruct.clans.shops.ShopManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopChunkListener extends SpigotModule<ShopManager> implements Listener {

    public ShopChunkListener(ShopManager manager) {
        super(manager, "ShopChunkListener");
    }
}