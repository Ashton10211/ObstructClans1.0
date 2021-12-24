package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
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
        if (player.getLocation().getY() < 40.0D || player.getLocation().getY() > 100.0D) {
            UtilMessage.message(player, "Clans", "You can only set home between 40 and 100 Y");
            return false;
        }
        clan.setHome(player.getLocation().getBlock().getLocation());
        UtilMessage.message(player, "Clans", "You set the Clan Home at " + ChatColor.YELLOW + "(" + (int) player.getLocation().getX() + ", " + (int) player.getLocation().getZ() + ")" + ChatColor.GRAY + ".");
        clan.inform(true, "Clans", ChatColor.AQUA + player.getName() + ChatColor.GRAY + " has set the Clan Home at " + ChatColor.YELLOW + "(" + (int)player.getLocation().getX() + "," + (int)player.getLocation().getZ() + ")" + ChatColor.GRAY + ".", player.getUniqueId());
        getExecutorService().execute(() -> getManager(ClanManager.class).saveClan(clan));
        return true;
    }
}