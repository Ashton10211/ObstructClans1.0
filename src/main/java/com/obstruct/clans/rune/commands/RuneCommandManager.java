package com.obstruct.clans.rune.commands;

import com.obstruct.core.spigot.framework.SpigotBasePlugin;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import org.bukkit.entity.Player;

public class RuneCommandManager extends CommandManager {

    public RuneCommandManager(SpigotBasePlugin plugin) {
        super(plugin, "RuneCommand");
    }

    @Override
    public void registerModules() {
        addModule(new BaseCommand(this));
        addModule(new GiveCommand(this));
    }

    class BaseCommand extends Command<Player> {

        public BaseCommand(CommandManager manager) {
            super(manager, "BaseCommand");
            setCommand("rune");
            setIndex(0);
            setRequiredArgs(0);
        }

        @Override
        public boolean execute(Player player, String[] args) {
            return false;
        }
    }
}