package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class DisableWeather extends SpigotModule<GameManager> implements Listener {

    public DisableWeather(GameManager manager) {
        super(manager, "DisableWeather");
    }

    @EventHandler
    public void setSunny(WeatherChangeEvent event) {
        if (!event.getWorld().hasStorm())
            event.setCancelled(true);
    }
}