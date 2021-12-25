package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.clans.clans.events.ClanUnclaimEvent;
import com.obstruct.clans.pillage.PillageManager;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilFormat;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

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
        if (land == null || !land.equals(clan)) {
            UtilMessage.message(player, "Clans", "This Territory is not owned by you.");
            return false;
        }
        if (getManager(PillageManager.class).isGettingPillaged(clan)) {
            UtilMessage.message(player, "Clans", "You cannot unclaim land while you are getting Pillaged.");
            return false;
        }
        Bukkit.getServer().getPluginManager().callEvent(new ClanUnclaimEvent(player, clan));
        return true;
    }
}