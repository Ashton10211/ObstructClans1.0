package com.obstruct.clans.rune.commands;

import com.obstruct.clans.rune.Rune;
import com.obstruct.clans.rune.RuneManager;
import com.obstruct.core.shared.utility.UtilJava;
import com.obstruct.core.spigot.framework.SpigotModule;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilItem;
import com.obstruct.core.spigot.utility.UtilMessage;
import com.obstruct.core.spigot.utility.UtilPlayer;
import org.bukkit.entity.Player;

public class GiveCommand extends Command<Player> {

    public GiveCommand(CommandManager manager) {
        super(manager, "GiveCommand");
        setCommand("give");
        setIndex(1);
        setRequiredArgs(2);
    }

    @Override
    public boolean execute(Player player, String[] args) {
        if(!(getManager(RuneManager.class).getModule(args[1]) instanceof Rune)) {
            UtilMessage.message(player, "Rune", args[1] + " doesn't exist");
            return false;
        }
        Rune rune = (Rune) getManager(RuneManager.class).getModule(args[1]);
        Player target = UtilPlayer.searchPlayer(player, args[2], true);
        if(target == null) {
            return false;
        }
        UtilItem.insert(target, rune.createRuneItem());
        return true;
    }
}