package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.*;
import com.obstruct.clans.clans.events.ClanJoinEvent;
import com.obstruct.clans.pillage.SiegeManager;
import com.obstruct.core.shared.client.Client;
import com.obstruct.core.shared.client.ClientDataRepository;
import com.obstruct.core.shared.redis.RedisManager;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import com.obstruct.core.spigot.utility.UtilTime;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
                if (target.isAdmin()) {
                    UtilMessage.message(player, "Clans", "You cannot join Admin Clans.");
                    return;
                }
                if (target.getMembers().size() >= target.getMaxMembers()) {
                    UtilMessage.message(player, "Clans", ChatColor.YELLOW + "Clan " + target.getName() + ChatColor.GRAY + " has too many members/allies.");
                    return;
                }
                if (getManager(SiegeManager.class).isGettingSieged(target)) {
                    UtilMessage.message(player, "Clans", "You cannot join a Clan while they have a Siege active.");
                    return;
                }
            }
            if (client.isAdministrating() || (target.getInviteeMap().containsKey(player.getUniqueId()) && !UtilTime.elapsed(target.getInviteeMap().get(player.getUniqueId()), 300000L))) {
                Bukkit.getPluginManager().callEvent(new ClanJoinEvent(player, target));
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