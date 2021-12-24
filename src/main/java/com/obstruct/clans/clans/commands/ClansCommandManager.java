package com.obstruct.clans.clans.commands;

import com.obstruct.clans.clans.commands.subcommands.*;
import com.obstruct.core.spigot.framework.SpigotBasePlugin;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import org.bukkit.entity.Player;

public class ClansCommandManager extends CommandManager {

    public ClansCommandManager(SpigotBasePlugin plugin) {
        super(plugin, "ClansCommandManager");
    }

    @Override
    public void registerModules() {
        addModule(new BaseCommand(this));
        addModule(new ClanAllyCommand(this));
        addModule(new ClanClaimCommand(this));
        addModule(new ClanCreateCommand(this));
        addModule(new ClanDemoteCommand(this));
        addModule(new ClanDisbandCommand(this));
        addModule(new ClanEnemyCommand(this));
        addModule(new ClanHomeCommand(this));
        addModule(new ClanInviteCommand(this));
        addModule(new ClanJoinCommand(this));
        addModule(new ClanKickCommand(this));
        addModule(new ClanLeaveCommand(this));
        addModule(new ClanMapCommand(this));
        addModule(new ClanNeutralCommand(this));
        addModule(new ClanPromoteCommand(this));
        addModule(new ClanSetHomeCommand(this));
        addModule(new ClanTrustCommand(this));
        addModule(new ClanUnclaimCommand(this));
    }

    class BaseCommand extends Command<Player> {

        public BaseCommand(CommandManager manager) {
            super(manager, "BaseCommand");
            setCommand("clan", "clans", "c", "faction", "fac", "f", "gang", "g");
            setIndex(0);
            setRequiredArgs(0);
        }

        @Override
        public boolean execute(Player player, String[] strings) {
            player.sendMessage("Open menu");
            return false;
        }
    }
}