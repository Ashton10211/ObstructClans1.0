package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.clans.clans.events.ClanNeutralEvent;
import com.obstruct.clans.pillage.SiegeManager;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClanNeutralCommand extends Command<Player> {

    public ClanNeutralCommand(CommandManager manager) {
        super(manager, "NeutralClanCommand");
        setCommand("neutral");
        setIndex(1);
        setRequiredArgs(2);
    }

    public boolean execute(Player player, String[] args) {
        Clan clan = getManager(ClanManager.class).getClan(player.getUniqueId());
        if (clan == null) {
            UtilMessage.message(player, "Clans", "You are not in a Clan.");
            return false;
        }
        if (!clan.hasMemberRole(player.getUniqueId(), MemberRole.ADMIN)) {
            UtilMessage.message(player, "Clans", "You must be an Admin or higher to neutral a Clan.");
            return false;
        }
        getExecutorService().execute(() -> {
            Clan target = getManager(ClanManager.class).searchClan(player, args[1], true);
            if (target == null) {
                return;
            }
            if (!clan.isAllied(target)) {
                UtilMessage.message(player, "Clans", "You are already neutral with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
                return;
            }
            Bukkit.getServer().getPluginManager().callEvent(new ClanNeutralEvent(player, clan, target));
//            if (target.getNeutralRequestMap().containsKey(clan.getName()) && !UtilTime.elapsed(target.getNeutralRequestMap().get(clan.getName()), 300000L)) {
//                Bukkit.getServer().getPluginManager().callEvent(new ClanNeutralEvent(player, clan, target));
//                return;
//            }
//            if (!clan.getNeutralRequestMap().containsKey(target.getName())) {
//                clan.getNeutralRequestMap().put(target.getName(), System.currentTimeMillis());
//                UtilMessage.message(player, "Clans", "You requested neutrality with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
//                clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " has requested neutrality with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".", player.getUniqueId());
//                target.inform(true, "Clans", getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + clan.getName() + ChatColor.GRAY + " has requested neutrality with your Clan.");
//                return;
//            }
//            UtilMessage.message(player, "Clans", "You have already requested to neutral with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
        });
        return true;
    }

    public void invalidArgsRequired(Player player) {
        UtilMessage.message(player, "Clans", "You did not input a Clan to Neutral.");
    }
}
