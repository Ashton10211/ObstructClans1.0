package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.clans.clans.events.ClanDisbandEvent;
import com.obstruct.clans.clans.events.ClanLeaveEvent;
import com.obstruct.clans.pillage.SiegeManager;
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

public class ClanLeaveCommand extends Command<Player> {

    private final Set<UUID> confirmSet = Collections.newSetFromMap(new WeakHashMap<>());

    public ClanLeaveCommand(CommandManager manager) {
        super(manager, "ClanLeaveCommand");
        setCommand("leave");
        setIndex(1);
        setRequiredArgs(1);
    }

    public boolean execute(Player player, String[] args) {
        ClanManager manager = getManager(ClanManager.class);
        Clan clan = manager.getClan(player.getUniqueId());
        if (clan == null) {
            UtilMessage.message(player, "Clans", "You are not in a Clan.");
            return true;
        }
//        if (getManager(SiegeManager.class).isGettingSieged(clan)) {
//            UtilMessage.message(player, "Clans", "You cannot leave your Clan while a Siege is active.");
//            return true;
//        }
        if(!clan.isAdmin()) {
            if (clan.getClanMember(player.getUniqueId()).getMemberRole() == MemberRole.LEADER && clan.getMembers().size() > 1) {
                UtilMessage.message(player, "Clans", "You must pass on Leadership before leaving.");
                return true;
            }
            if (!confirmSet.contains(player.getUniqueId())) {
                new FancyMessage(ChatColor.BLUE + "Clans> " + ChatColor.RED + ChatColor.BOLD + "Click here to leave your Clan.").tooltip(ChatColor.GOLD + "Clicking this text will make you leave your Clan.").command("/c leave").send(player);
                confirmSet.add(player.getUniqueId());
                return false;
            }
            if (clan.getClanMember(player.getUniqueId()).getMemberRole() == MemberRole.LEADER && clan.getMembers().size() == 1) {
                Bukkit.getServer().getPluginManager().callEvent(new ClanDisbandEvent(player, clan, ClanDisbandEvent.DisbandReason.PLAYER));
                return true;
            }
        }
        Bukkit.getServer().getPluginManager().callEvent(new ClanLeaveEvent(player, clan));
        return true;
    }
}