package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanRelation;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.clans.pillage.PillageManager;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClanLeaveCommand extends Command<Player> {

    public ClanLeaveCommand(CommandManager manager) {
        super(manager, "ClanLeaveCommand");
        setCommand("leave");
        setIndex(1);
        setRequiredArgs(1);
    }

    public boolean execute(Player player, String[] args) {
        ClanManager manager = getManager(ClanManager.class);
        Clan clan = manager.getClan(player.getUniqueId());
        if (clan == null) {
            UtilMessage.message(player, "Clans", "You are not in a Clan.");
            return true;
        }
        if (getManager(PillageManager.class).isGettingPillaged(clan)) {
            UtilMessage.message(player, "Clans", "You cannot leave your Clan while you are getting Pillaged.");
            return true;
        }
        if (clan.getClanMember(player.getUniqueId()).getMemberRole() == MemberRole.LEADER && clan.getMembers().size() == 1) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                ClanRelation clanRelation = manager.getClanRelation(clan, manager.getClan(online));
                UtilMessage.message(online, "Clans", clanRelation.getSuffix() + player.getName() + ChatColor.GRAY + " has disbanded " + clanRelation.getSuffix() + "Clan " + clan.getName() + ChatColor.GRAY + ".");
            }
            manager.removeClan(clan);
            getExecutorService().execute(() -> manager.deleteClan(clan));
            return true;
        }
        if (clan.getClanMember(player.getUniqueId()).getMemberRole() == MemberRole.LEADER && clan.getMembers().size() > 1) {
            UtilMessage.message(player, "Clans", "You must pass on Leadership before leaving.");
            return true;
        }
        clan.getMembers().remove(clan.getClanMember(player.getUniqueId()));
        UtilMessage.message(player, "Clans", "You left " + ChatColor.YELLOW + "Clan " + clan.getName() + ChatColor.GRAY + ".");
        clan.inform(true, "Clans", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " left the Clan.", player.getUniqueId());
        getExecutorService().execute(() -> manager.saveClan(clan));
        return true;
    }
}