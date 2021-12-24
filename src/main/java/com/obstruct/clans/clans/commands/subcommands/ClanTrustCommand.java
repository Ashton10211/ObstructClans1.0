package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanRelation;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import com.obstruct.core.spigot.utility.UtilTime;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClanTrustCommand extends Command<Player> {

    public ClanTrustCommand(CommandManager manager) {
        super(manager, "ClanTrustCommand");
        setCommand("trust");
        setIndex(1);
        setRequiredArgs(2);
    }


    public boolean execute(Player player, String[] args) {
        Clan clan = getManager(ClanManager.class).getClan(player);
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
                UtilMessage.message(player, "Clans", "You must be an Admin or higher to trust a Clan.");
                return;
            }
            if (clan.equals(target)) {
                UtilMessage.message(player, "Clans", "You cannot trust yourself.");
                return;
            }
            if (clan.isTrusted(target)) {
                clan.getAllianceMap().put(target.getName(), false);
                target.getAllianceMap().put(clan.getName(), false);
                UtilMessage.message(player, "Clans", "You have revoked trust with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
                clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " has revoked trust with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".", player.getUniqueId());
                target.inform(true, "Clans", getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + clan.getName() + ChatColor.GRAY + " has revoked trust with your Clan.");

                getManager(ClanManager.class).saveClan(clan);
                getManager(ClanManager.class).saveClan(target);
                return;
            }
            ClanRelation clanRelation = getManager(ClanManager.class).getClanRelation(clan, target);
            if (!clan.isAllied(target)) {
                UtilMessage.message(player, "Clans", "You must be allies with " + clanRelation.getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + " before trusting them.");
                return;
            }
            if (target.getTrustRequestMap().containsKey(clan.getName()) && !UtilTime.elapsed(target.getTrustRequestMap().get(clan.getName()), 300000L)) {
                target.getTrustRequestMap().remove(clan.getName());
                clan.getTrustRequestMap().remove(target.getName());
                clan.getAllianceMap().put(target.getName(), true);
                target.getAllianceMap().put(clan.getName(), true);

                UtilMessage.message(player, "Clans", "You have accepted trust with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
                clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " accepted trust with your Clan.", player.getUniqueId());
                target.inform(true, "Clans", getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + clan.getName() + ChatColor.GRAY + " has accepted trust with your Clan.");

                getManager(ClanManager.class).saveClan(clan);
                getManager(ClanManager.class).saveClan(target);
                return;
            }
            if (!clan.getTrustRequestMap().containsKey(target.getName())) {
                clan.getTrustRequestMap().put(target.getName(), System.currentTimeMillis());
                UtilMessage.message(player, "Clans", "You requested trust with " + clanRelation.getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
                clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " has requested trust with " + clanRelation.getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".", player.getUniqueId());
                target.inform(true, "Clans", clanRelation.getSuffix() + "Clan " + clan.getName() + ChatColor.GRAY + " has requested trust with your Clan.");
                return;
            }
            UtilMessage.message(player, "Clans", "You have already requested to trust with " + clanRelation.getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
        });
        return false;
    }


    public void invalidArgsRequired(Player sender) {
        UtilMessage.message(sender, "Clans", "You did not input a Clan to Trust.");
    }
}
