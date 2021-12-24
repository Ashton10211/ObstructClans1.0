package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.*;
import com.obstruct.clans.pillage.PillageManager;
import com.obstruct.core.shared.client.Client;
import com.obstruct.core.shared.client.ClientDataRepository;
import com.obstruct.core.shared.redis.RedisManager;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import com.obstruct.core.spigot.utility.UtilTime;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ClanJoinCommand extends Command<Player> {

    public ClanJoinCommand(CommandManager manager) {
        super(manager, "ClanJoinCommand");
        setCommand("join");
        setIndex(1);
        setRequiredArgs(2);
    }


    public boolean execute(Player player, String[] args) {
        Clan clan = (getManager(ClanManager.class)).getClan(player.getUniqueId());
        if (clan != null) {
            UtilMessage.message(player, "Clans", "You are already in a Clan.");
            return false;
        }
        getExecutorService().execute(() -> {
            Clan target = getManager(ClanManager.class).searchClan(player, args[1], true);
            if (target == null) {
                return;
            }
            Client client = getManager(RedisManager.class).getModule(ClientDataRepository.class).getClient(player.getUniqueId());
            if (!client.isAdministrating()) {
                if (target instanceof AdminClan) {
                    UtilMessage.message(player, "Clans", "You cannot join Admin Clans.");
                    return;
                }
                if (target.getMembers().size() + target.getAllianceMap().size() >= 8) {
                    UtilMessage.message(player, "Clans", ChatColor.YELLOW + "Clan " + target.getName() + ChatColor.GRAY + " has too many members/allies.");
                    return;
                }
                if (getManager(PillageManager.class).isGettingPillaged(target)) {
                    UtilMessage.message(player, "Clans", "You cannot join a Clan while they are getting Pillaged.");
                    return;
                }
            }
            if (client.isAdministrating() || (target.getInviteeMap().containsKey(player.getUniqueId()) && !UtilTime.elapsed(target.getInviteeMap().get(player.getUniqueId()), 300000L))) {
                target.getInviteeMap().remove(player.getUniqueId());
                target.getMembers().add(new ClanMember(player.getUniqueId(), MemberRole.RECRUIT));
                UtilMessage.message(player, "Clans", "You joined " + ChatColor.YELLOW + "Clan " + target.getName() + ChatColor.GRAY + ".");
                target.inform(true, "Clans", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " joined the Clan.", player.getUniqueId());
                //Already running async
                getManager(ClanManager.class).saveClan(target);
                return;
            }
            UtilMessage.message(player, "Clans", "You have not been invited to " + ChatColor.YELLOW + "Clan " + target.getName() + ChatColor.GRAY + ".");
        });
        return false;
    }

    public void invalidArgsRequired(Player sender) {
        UtilMessage.message(sender, "Clans", "You did not input a Clan to Join.");
    }
}