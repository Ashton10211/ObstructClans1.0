package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.clans.clans.events.ClanKickEvent;
import com.obstruct.clans.pillage.PillageManager;
import com.obstruct.core.shared.client.Client;
import com.obstruct.core.shared.client.ClientDataRepository;
import com.obstruct.core.shared.redis.RedisManager;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClanKickCommand extends Command<Player> {

    public ClanKickCommand(CommandManager manager) {
        super(manager, "ClanKickCommand");
        setCommand("kick");
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
            Client target = getManager(ClanManager.class).searchMember(player, args[1], true);
            if (target == null) {
                return;
            }
            if (target.getUuid().equals(player.getUniqueId())) {
                UtilMessage.message(player, "Clans", "You cannot kick yourself.");
                return;
            }
            if (!clan.equals(getManager(ClanManager.class).getClan(target.getUuid()))) {
                UtilMessage.message(player, "Clans", ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " is not apart of your Clan.");
                return;
            }
            Client client = getManager(RedisManager.class).getModule(ClientDataRepository.class).getClient(player.getUniqueId());

            if (!client.isAdministrating()) {
                if (!clan.hasMemberRole(player.getUniqueId(), MemberRole.ADMIN)) {
                    UtilMessage.message(player, "Clans", "Only the Clan Leader and Admins can kick members.");
                    return;
                }
                if (!clan.hasMemberRole(player.getUniqueId(), clan.getClanMember(target.getUuid()).getMemberRole())) {
                    UtilMessage.message(player, "Clans", "You do not outrank " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
                    return;
                }
            }
            if (getManager(PillageManager.class).isGettingPillaged(clan)) {
                UtilMessage.message(player, "Clans", "You cannot kick a player while you are getting Pillaged.");
                return;
            }
            Bukkit.getPluginManager().callEvent(new ClanKickEvent(player, target, clan));
        });
        return true;
    }

    public void invalidArgsRequired(Player sender) {
        UtilMessage.message(sender, "Clans", "You did not input a Player to Kick.");
    }
}