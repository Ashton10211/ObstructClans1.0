package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.AdminClan;
import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.clans.pillage.PillageManager;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClanEnemyCommand extends Command<Player> {

    public ClanEnemyCommand(CommandManager manager) {
        super(manager, "ClanEnemyCommand");
        setCommand("enemy");
        setIndex(1);
        setRequiredArgs(2);
    }

    public boolean execute(Player player, String[] args) {
        Clan clan = getManager(ClanManager.class).getClan(player);
        if (clan == null) {
            UtilMessage.message(player, "Clans", "You are not in a Clan.");
            return false;
        }
        if (!clan.hasMemberRole(player.getUniqueId(), MemberRole.ADMIN)) {
            UtilMessage.message(player, "Clans", "You must be an Admin or higher to enemy a Clan.");
            return false;
        }
        getExecutorService().execute(() -> {
            Clan target = getManager(ClanManager.class).searchClan(player, args[1], true);
            if (target == null) {
                return;
            }
            if (clan.equals(target)) {
                UtilMessage.message(player, "Clans", "You cannot enemy yourself.");
                return;
            }
            if (clan.isEnemy(target)) {
                UtilMessage.message(player, "Clans", "You are already enemies with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
                return;
            }
            if (target instanceof AdminClan) {
                UtilMessage.message(player, "Clans", "You cannot wage war with Admin Clans.");
                return;
            }
            if (getManager(PillageManager.class).isPillaging(clan, target) || getManager(PillageManager.class).isPillaged(clan, target)) {
                UtilMessage.message(player, "Clans", "You cannot enemy " + ChatColor.LIGHT_PURPLE + "Clan " + target.getName() + ChatColor.GRAY + " while a Pillage is active.");
                return;
            }
            if (clan.isAllied(target)) {
                clan.getAllianceMap().remove(target.getName());
                target.getAllianceRequestMap().remove(clan.getName());
            }
            clan.getAllianceRequestMap().remove(target.getName());
            target.getAllianceRequestMap().remove(clan.getName());
            clan.getEnemyMap().put(target.getName(), 0);
            target.getEnemyMap().put(clan.getName(), 0);

            UtilMessage.message(player, "Clans", "You waged war with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");
            target.inform(true, "Clans", getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + clan.getName() + ChatColor.GRAY + " waged war with your Clan.");
            clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " waged war with " + getManager(ClanManager.class).getClanRelation(clan, target).getSuffix() + "Clan " + target.getName() + ChatColor.GRAY + ".");

            //Already running async
            getManager(ClanManager.class).saveClan(clan);
            getManager(ClanManager.class).saveClan(target);
        });
        return true;
    }

    public void invalidArgsRequired(Player sender) {
        UtilMessage.message(sender, "Clans", "You did not input a Clan to Enemy.");
    }
}