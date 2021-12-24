package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.AdminClan;
import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.clans.pillage.PillageManager;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import com.obstruct.core.spigot.utility.UtilTime;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClanAllyCommand extends Command<Player> {

    public ClanAllyCommand(CommandManager manager) {
        super(manager, "AllyClanCommand");
        setCommand("ally");
        setIndex(1);
        setRequiredArgs(2);
    }

    public boolean execute(Player player, String[] args) {
        Clan clan = getManager(ClanManager.class).getClan(player.getUniqueId());
        if (clan == null) {
            UtilMessage.message(player, "Clans", "You are not in a Clan.");
            return false;
        }
        getExecutorService().execute(() -> {
            Clan target = getManager(ClanManager.class).searchClan(player, args[1], true);
            if (target == null) {
                return;
            }
            if (!clan.hasMemberRole(player.getUniqueId(), MemberRole.ADMIN)) {
                UtilMessage.message(player, "Clans", "You must be an Admin or higher to ally a Clan.");
                return;
            }
            if (clan.equals(target)) {
                UtilMessage.message(player, "Clans", "You cannot request an alliance with yourself.");
                return;
            }
            if (target instanceof AdminClan) {
                UtilMessage.message(player, "Clans", "You cannot request an alliance with Admin Clans.");
                return;
            }
            if (getManager(PillageManager.class).isPillaging(clan, target) || getManager(PillageManager.class).isPillaged(clan, target)) {
                UtilMessage.message(player, "Clans", "You cannot ally " + ChatColor.LIGHT_PURPLE + "Clan " + target.getName() + ChatColor.GRAY + " while a Pillage is active.");
                return;
            }
            if (clan.isAllied(target)) {
                UtilMessage.message(player, "Clans", "Your Clan already has an alliance with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
                return;
            }
            if (clan.isEnemy(target)) {
                UtilMessage.message(player, "Clans", "You must be neutral with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + " before requesting an alliance.");
                return;
            }
            if (clan.getAllianceMap().size() + clan.getMembers().size() >= 8) {
                UtilMessage.message(player, "Clans", "Your Clan has too many members/allies.");
                return;
            }
            if (target.getAllianceMap().size() + target.getMembers().size() >= 8) {
                UtilMessage.message(player, "Clans", ChatColor.YELLOW + "Clan " + target.getName() + ChatColor.GRAY + " has too many members/allies.");
                return;
            }
            if (target.getAllianceRequestMap().containsKey(clan.getName()) && !UtilTime.elapsed(target.getAllianceRequestMap().get(clan.getName()), 300000L)) {
                clan.getAllianceRequestMap().remove(target.getName());
                target.getAllianceRequestMap().remove(clan.getName());
                clan.getAllianceMap().put(target.getName(), false);
                target.getAllianceMap().put(clan.getName(), false);

                UtilMessage.message(player, "Clans", "You have accepted alliance with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
                target.inform(true, "Clans", getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + clan.getName() + ChatColor.GRAY + " has accepted alliance with your Clan.");
                clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " has accepted alliance with your Clan.", player.getUniqueId());
                //Already running async
                getManager(ClanManager.class).saveClan(clan);
                getManager(ClanManager.class).saveClan(target);
                return;
            }
            if (!clan.getAllianceRequestMap().containsKey(target.getName())) {
                clan.getAllianceRequestMap().put(target.getName(), System.currentTimeMillis());
                UtilMessage.message(player, "Clans", "You requested alliance with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
                clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " has requested alliance with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".", player.getUniqueId());
                target.inform(true, "Clans", getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + clan.getName() + ChatColor.GRAY + " has requested alliance with your Clan.");
                return;
            }
            UtilMessage.message(player, "Clans", "Your Clan has already requested an alliance with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
        });
        return true;
    }

    public void invalidArgsRequired(Player sender) {
        UtilMessage.message(sender, "Clans", "You did not input a Clan to Ally.");
    }
}
