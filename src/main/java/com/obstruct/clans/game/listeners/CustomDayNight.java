package com.obstruct.clans.game.listeners;

import com.obstruct.clans.game.GameManager;
import com.obstruct.core.shared.update.Update;
import com.obstruct.core.shared.update.Updater;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class CustomDayNight extends SpigotModule<GameManager> implements Updater {

    public CustomDayNight(GameManager manager) {
        super(manager, "CustomDayNight");
    }

    @Update(ticks = 2)
    public void onUpdate() {
        World world = Bukkit.getWorlds().get(0);
        if(world.getTime() > 13000L) {
            world.setTime(world.getTime() + 12L);
        }
    }
}
