package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanMember;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.clans.clans.events.ClanPromoteEvent;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClanPromoteCommand extends Command<Player> {

    public ClanPromoteCommand(CommandManager manager) {
        super(manager, "ClanPromoteCommand");
        setCommand("promote");
        setIndex(1);
        setRequiredArgs(2);
    }


    public boolean execute(Player player, String[] args) {
        Clan clan = getManager(ClanManager.class).getClan(player);
        if (clan == null) {
            UtilMessage.message(player, "Clans", "You are not in a Clan.");
            return false;
        }
        ClanMember target = getManager(ClanManager.class).searchMember(player, args[1], true);
        if (target == null) {
            return false;
        }
        if (!clan.hasMemberRole(player.getUniqueId(), MemberRole.ADMIN)) {
            UtilMessage.message(player, "Clans", "You must be an Admin or higher to promote a Player.");
            return false;
        }
        if (player.getUniqueId().equals(target.getUuid())) {
            UtilMessage.message(player, "Clans", "You cannot promote yourself.");
            return false;
        }
        if (!clan.equals(getManager(ClanManager.class).getClan(target.getUuid()))) {
            UtilMessage.message(player, "Clans", ChatColor.YELLOW + target.getPlayerName() + ChatColor.GRAY + " is not apart of your Clan.");
            return false;
        }
        if (clan.getClanMember(player.getUniqueId()).getMemberRole().ordinal() <= clan.getClanMember(target.getUuid()).getMemberRole().ordinal()) {
            UtilMessage.message(player, "Clans", "You do not outrank " + ChatColor.YELLOW + target.getPlayerName() + ChatColor.GRAY + ".");
            return false;
        }
        if (clan.getClanMember(target.getUuid()).getMemberRole() == MemberRole.LEADER) {
            UtilMessage.message(player, "Clans", "You cannot promote " + ChatColor.YELLOW + target.getPlayerName() + ChatColor.GRAY + " any further.");
            return false;
        }
        Bukkit.getPluginManager().callEvent(new ClanPromoteEvent(player, target, clan));
        return true;
    }

    public void invalidArgsRequired(Player sender) {
        UtilMessage.message(sender, "Clans", "You did not input a Player to Promote.");
    }
}