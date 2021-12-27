package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.clans.clans.events.ClanUnclaimAllEvent;
import com.obstruct.clans.clans.events.ClanUnclaimEvent;
import com.obstruct.clans.pillage.SiegeManager;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClanUnclaimCommand extends Command<Player> {

    public ClanUnclaimCommand(CommandManager manager) {
        super(manager, "ClanUnclaimCommand");
        setCommand("unclaim");
        setIndex(1);
        setRequiredArgs(1);
    }


    public boolean execute(Player player, String[] args) {
        Clan clan = getManager(ClanManager.class).getClan(player);
        if (clan == null) {
            UtilMessage.message(player, "Clans", "You are not in a Clan.");
            return false;
        }
        if (!clan.hasMemberRole(player.getUniqueId(), MemberRole.ADMIN)) {
            UtilMessage.message(player, "Clans", "You must be an Admin or higher to unclaim land.");
            return false;
        }
        Clan land = getManager(ClanManager.class).getClan(player.getLocation().getChunk());
        if (getManager(SiegeManager.class).isGettingSieged(clan)) {
            UtilMessage.message(player, "Clans", "You cannot unclaim land while a Siege is active.");
            return false;
        }
        if(args.length >= 2 && args[1].equalsIgnoreCase("all")) {
            Bukkit.getServer().getPluginManager().callEvent(new ClanUnclaimAllEvent(player, clan));
            return false;
        }
        if (land == null || !land.equals(clan)) {
            UtilMessage.message(player, "Clans", "This Territory is not owned by you.");
            return false;
        }
        Bukkit.getServer().getPluginManager().callEvent(new ClanUnclaimEvent(player, player.getLocation().getChunk(), clan));
        return true;
    }
}