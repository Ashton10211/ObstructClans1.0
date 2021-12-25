package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanMember;
import com.obstruct.clans.clans.MemberRole;
import com.obstruct.clans.clans.events.ClanCreateEvent;
import com.obstruct.core.shared.client.Client;
import com.obstruct.core.shared.client.ClientDataRepository;
import com.obstruct.core.shared.redis.RedisManager;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.regex.Pattern;

public class ClanCreateCommand extends Command<Player> {

    public ClanCreateCommand(CommandManager manager) {
        super(manager, "CreateClanCommand");
        setCommand("create");
        setIndex(1);
        setRequiredArgs(2);
    }

    @Override
    public boolean execute(Player player, String[] args) {
        if (getManager(ClanManager.class).getClan(player) != null) {
            UtilMessage.message(player, "Clans", "You are already in a Clan.");
            return true;
        }
        if (getManager(ClanManager.class).getClan(args[1]) != null) {
            UtilMessage.message(player, "Clans", "Clan name is already in use.");
            return true;
        }
        Client client = getManager(RedisManager.class).getModule(ClientDataRepository.class).getClient(player.getUniqueId());
        if (!client.isAdministrating()) {
            if (args[1].length() < 3) {
                UtilMessage.message(player, "Clans", "Clan name is too short. Minimum length is " + ChatColor.YELLOW + "3" + ChatColor.GRAY + ".");
                return true;
            }
            if (args[1].length() > 14) {
                UtilMessage.message(player, "Clans", "Clan name is too long. Maximum length is " + ChatColor.YELLOW + "14" + ChatColor.GRAY + ".");
                return false;
            }
            if (Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE).matcher(args[1]).find()) {
                UtilMessage.message(player, "Clans", "You cannot have special characters in your Clan name.");
                return false;
            }
        }
        for (Command<?> module : getManager().getModules()) {
            if (Arrays.asList(module.getCommand()).contains(args[1])) {
                UtilMessage.message(player, "Clans", "You cannot use a Command as your Clan name.");
                return false;
            }
        }
        Clan clan = new Clan(args[1]);
        if(client.isAdministrating()) {
            clan.setAdmin(true);
            clan.setSafe(true);
        }
        Bukkit.getServer().getPluginManager().callEvent(new ClanCreateEvent(player, clan));
        return true;
    }

    public void invalidArgsRequired(Player sender) {
        UtilMessage.message(sender, "Clans", "You did not input a Clan name.");
    }
}