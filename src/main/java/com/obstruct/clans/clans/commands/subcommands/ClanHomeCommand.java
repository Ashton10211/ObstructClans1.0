package com.obstruct.clans.clans.commands.subcommands;

import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.events.ClanHomeEvent;
import com.obstruct.core.shared.client.Client;
import com.obstruct.core.shared.client.ClientDataRepository;
import com.obstruct.core.shared.redis.RedisManager;
import com.obstruct.core.spigot.framework.command.Command;
import com.obstruct.core.spigot.framework.command.CommandManager;
import com.obstruct.core.spigot.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClanHomeCommand extends Command<Player> {

    public ClanHomeCommand(CommandManager manager) {
        super(manager, "ClanHomeCommand");
        setCommand("home");
        setIndex(1);
        setRequiredArgs(1);
    }


    public boolean execute(Player player, String[] args) {
        Clan clan = getManager(ClanManager.class).getClan(player.getUniqueId());
        if (clan == null) {
            UtilMessage.message(player, "Clans", "You are not in a Clan.");
            return false;
        }
        if (clan.getHome() == null) {
            UtilMessage.message(player, "Clans", "Your Clan does not have a Home set.");
            return false;
        }
        Client client = getManager(RedisManager.class).getModule(ClientDataRepository.class).getClient(player.getUniqueId());
        if (!client.isAdministrating()) {
            Clan land = getManager(ClanManager.class).getClan(player.getLocation().getChunk());
            if (land == null || land.getName().toLowerCase().contains(" spawn")) {
                UtilMessage.message(player, "Clans", "You can only use Clan Home from Spawn.");
                return false;
            }
        }
        Bukkit.getPluginManager().callEvent(new ClanHomeEvent(player, clan));
        return true;
    }
}
