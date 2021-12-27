package com.obstruct.clans.map.commands;

import com.obstruct.clans.map.MapManager;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import org.bukkit.entity.Player;

public class SaveMapCommand extends Command<Player> {
    public SaveMapCommand(CommandManager manager) {
        super(manager, "SaveMapCommand");
        setCommand("save");
        setRequiredArgs(1);
        setIndex(1);
    }


    public boolean execute(Player player, String[] strings) {
        getManager(MapManager.class).saveMapData();
        return false;
    }
}
