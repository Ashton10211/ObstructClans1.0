package com.obstruct.clans.map.commands;

import com.obstruct.clans.map.MapManager;
import com.obstruct.clans.map.renderer.MinimapRenderer;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import com.obstruct.core.spigot.utility.UtilTime;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.map.MapView;

public class LoadMapCommand extends Command<Player> {

    public LoadMapCommand(CommandManager manager) {
        super(manager, "LoadMapCommand");
        setCommand("load");
        setRequiredArgs(1);
        setIndex(1);
    }


    public boolean execute(Player player, String[] strings) {
        long epoc = System.currentTimeMillis();

        MapView map = Bukkit.getMap((short)0);
        if (map == null) {
            map = Bukkit.createMap(Bukkit.getWorld("world"));
        }
        MinimapRenderer minimapRenderer = (MinimapRenderer)map.getRenderers().get(0);

        minimapRenderer.getWorldCacheMap().clear();

        getManager(MapManager.class).loadMapData(minimapRenderer);

        UtilMessage.message(player, "Map", "Map loaded in " + ChatColor.GREEN + UtilTime.getTime((System.currentTimeMillis() - epoc), UtilTime.TimeUnit.SECONDS, 2) + ChatColor.GRAY + ".");
        return false;
    }
}