package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.clans.clans.events.ClanDemoteEvent;
import com.obstruct.core.shared.client.Client;
import com.obstruct.core.shared.client.ClientDataRepository;
import com.obstruct.core.shared.redis.RedisManager;
import com.obstruct.core.shared.utility.UtilDebug;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilFormat;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClanDemoteCommand extends Command<Player> {

    public ClanDemoteCommand(CommandManager manager) {
        super(manager, "ClanDemoteCommand");
        setCommand("demote");
        setIndex(1);
        setRequiredArgs(2);
    }

    @Override
    public boolean execute(Player player, String[] args) {
        Clan clan = getManager(ClanManager.class).getClan(player);
        if (clan == null) {
            UtilMessage.message(player, "Clans", "You are not in a Clan.");
            return false;
        }
        getExecutorService().execute(() -> {
            Client target = getManager(ClanManager.class).searchMember(player, args[1], true);
            if (target == null) {
                return;
            }
            Client client = getManager(RedisManager.class).getModule(ClientDataRepository.class).getClient(player.getUniqueId());
            if (!client.isAdministrating()) {
                if (!clan.hasMemberRole(player.getUniqueId(), MemberRole.ADMIN)) {
                    UtilMessage.message(player, "Clans", "You must be an Admin or higher to demote a Player.");
                    return;
                }
                if (player.getUniqueId().equals(target.getUuid())) {
                    UtilMessage.message(player, "Clans", "You cannot demote yourself.");
                    return;
                }
                if (!clan.equals(getManager(ClanManager.class).getClan(target.getUuid()))) {
                    UtilMessage.message(player, "Clans", ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " is not apart of your Clan.");
                    return;
                }
                if (clan.getClanMember(player.getUniqueId()).getMemberRole().ordinal() <= clan.getClanMember(target.getUuid()).getMemberRole().ordinal()) {
                    UtilMessage.message(player, "Clans", "You do not outrank " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
                    return;
                }
                if (clan.getClanMember(target.getUuid()).getMemberRole().ordinal() == 0) {
                    UtilMessage.message(player, "Clans", "You cannot demote " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " any further.");
                    return;
                }
            }
            Bukkit.getServer().getPluginManager().callEvent(new ClanDemoteEvent(player, target, clan));
        });
        return true;
    }

    public void invalidArgsRequired(Player sender) {
        UtilMessage.message(sender, "Clans", "You did not input a Player to Demote.");
    }
}