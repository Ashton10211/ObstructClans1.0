package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanRelation;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.core.shared.client.ClientDataRepository;
import com.obstruct.core.shared.redis.RedisManager;
import com.obstruct.core.spigot.common.fancy.FancyMessage;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

public class ClanDisbandCommand extends Command<Player> {

    private final Set<UUID> confirmSet = Collections.newSetFromMap(new WeakHashMap<>());

    public ClanDisbandCommand(CommandManager manager) {
        super(manager, "DisbandClanCommand");
        setCommand("disband");
        setIndex(1);
        setRequiredArgs(1);
    }

    @Override
    public boolean execute(Player player, String[] args) {
        ClanManager manager = getManager(ClanManager.class);
        Clan clan = manager.getClan(player);
        if(clan == null) {
            UtilMessage.message(player, "Clans", "You are not in a Clan.");
            return false;
        }
        if(!getManager(RedisManager.class).getModule(ClientDataRepository.class).getClient(player.getUniqueId()).isAdministrating()) {
            if(!clan.hasMemberRole(player, MemberRole.LEADER)) {
                UtilMessage.message(player, "Clans", "You must be Leader to disband your Clan.");
                return false;
            }
        }
        if(!confirmSet.contains(player.getUniqueId())) {
            new FancyMessage(ChatColor.BLUE + "Clans> " + ChatColor.RED + ChatColor.BOLD + "Click here to permanently disband this Clan.").tooltip(ChatColor.GOLD + "Clicking this text will disband your Clan.").command("/c disband").send(player);
            confirmSet.add(player.getUniqueId());
            return false;
        }
        confirmSet.remove(player.getUniqueId());
        for (Player online : Bukkit.getOnlinePlayers()) {
            ClanRelation clanRelation = manager.getClanRelation(clan, manager.getClan(online));
            UtilMessage.message(online, "Clans", clanRelation.getSuffix() + player.getName() + ChatColor.GRAY + " has disbanded " + clanRelation.getSuffix() + "Clan " + clan.getName() + ChatColor.GRAY + ".");
        }
        getManager(ClanManager.class).removeClan(clan);
        getExecutorService().execute(() -> getManager(ClanManager.class).deleteClan(clan));
        return true;
    }
}