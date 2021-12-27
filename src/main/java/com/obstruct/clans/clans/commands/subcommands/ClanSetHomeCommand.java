package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.clans.clans.events.ClanSetHomeEvent;
import com.obstruct.clans.pillage.SiegeManager;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClanSetHomeCommand extends Command<Player> {

    public ClanSetHomeCommand(CommandManager manager) {
        super(manager, "ClanSetHomeCommand");
        setCommand("sethome");
        setIndex(1);
        setRequiredArgs(1);
    }

    public boolean execute(Player player, String[] args) {
        Clan clan = getManager(ClanManager.class).getClan(player);
        if (clan == null) {
            UtilMessage.message(player, "Clans", "You are not in a Clan.");
            return false;
        }
        if (!clan.hasMemberRole(player.getUniqueId(), MemberRole.ADMIN)) {
            UtilMessage.message(player, "Clans", "You must be an Admin or higher to set Clan Home.");
            return false;
        }
        if (getManager(ClanManager.class).getClan(player.getLocation().getChunk()) == null || !getManager(ClanManager.class).getClan(player.getLocation().getChunk()).equals(clan)) {
            UtilMessage.message(player, "Clans", "You must set your Clan Home in your own Territory.");
            return false;
        }
        if (getManager(SiegeManager.class).isGettingSieged(clan)) {
            UtilMessage.message(player, "Clans", "You cannot set Clan home while a Siege is active.");
            return false;
        }
        if (player.getLocation().getY() < 40.0D || player.getLocation().getY() > 100.0D) {
            UtilMessage.message(player, "Clans", "You can only set home between 40 and 100 Y");
            return false;
        }
        Bukkit.getPluginManager().callEvent(new ClanSetHomeEvent(player, clan));
        return true;
    }
}