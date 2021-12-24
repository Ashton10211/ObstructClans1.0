package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanRelation;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.clans.pillage.PillageManager;
import com.obstruct.core.shared.client.Client;
import com.obstruct.core.shared.client.ClientDataRepository;
import com.obstruct.core.shared.redis.RedisManager;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import com.obstruct.core.spigot.utility.UtilPlayer;
import com.obstruct.core.spigot.utility.UtilTime;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClanInviteCommand extends Command<Player> {

    public ClanInviteCommand(CommandManager manager) {
        super(manager, "ClanInviteCommand");
        setCommand("invite");
        setIndex(1);
        setRequiredArgs(2);
    }

    public boolean execute(Player player, String[] args) {
        Clan clan = getManager(ClanManager.class).getClan(player);
        if (clan == null) {
            UtilMessage.message(player, "Clans", "You are not in a Clan.");
            return false;
        }
        Client client = getManager(RedisManager.class).getModule(ClientDataRepository.class).getClient(player.getUniqueId());
        if (!client.isAdministrating() && !clan.hasMemberRole(player, MemberRole.ADMIN)) {
            UtilMessage.message(player, "Clans", "You must be an Admin or higher to invite a Player.");
            return false;
        }
        Player target = UtilPlayer.searchPlayer(player, args[1], true);
        if (target == null) {
            return false;
        }
        if (player.getUniqueId().equals(target.getUniqueId())) {
            UtilMessage.message(player, "Clans", "You cannot invite yourself.");
            return false;
        }
        Clan c = getManager(ClanManager.class).getClan(target.getUniqueId());
        if (c != null) {
            if (c.equals(clan)) {
                UtilMessage.message(player, "Clans", ChatColor.AQUA + target.getName() + ChatColor.GRAY + " is already apart of your Clan.");
                return false;
            }
            ClanRelation clanRelation = getManager(ClanManager.class).getClanRelation(clan, c);
            UtilMessage.message(player, "Clans", ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " is apart of " + clanRelation.getSuffix() + "Clan " + c.getName() + ChatColor.GRAY + ".");
            return false;
        }
        if (clan.getInviteeMap().containsKey(target.getUniqueId()) && !UtilTime.elapsed(clan.getInviteeMap().get(target.getUniqueId()), 300000L)) {
            UtilMessage.message(player, "Clans", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " has already been invited to your Clan.");
            return false;
        }
        if (clan.getMembers().size() + clan.getAllianceMap().size() >= 8) {
            UtilMessage.message(player, "Clans", "Your Clan has too many members/allies.");
            return false;
        }
        if (getManager(PillageManager.class).isGettingPillaged(clan)) {
            UtilMessage.message(player, "Clans", "You cannot invite a Player while your Clan has a Pillage active.");
            return false;
        }
        clan.getInviteeMap().put(target.getUniqueId(), System.currentTimeMillis());
        clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " invited " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " to the Clan.", player.getUniqueId());
        UtilMessage.message(player, "Clans", "You invited " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " to " + ChatColor.YELLOW + "Clan " + clan.getName() + ChatColor.GRAY + ".");
        UtilMessage.message(target, "Clans", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " has invited you to join " + ChatColor.YELLOW + "Clan " + clan.getName() + ChatColor.GRAY + ".");
        return true;
    }

    public void invalidArgsRequired(Player sender) {
        UtilMessage.message(sender, "Clans", "You did not input a Player to Invite.");
    }
}
