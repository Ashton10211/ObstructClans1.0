package com.obstruct.clans.clans.listeners;

import com.obstruct.clans.clans.ChatType;
import com.obstruct.clans.clans.Clan;
import com.obstruct.clans.clans.ClanManager;
import com.obstruct.clans.clans.ClanRelation;
import com.obstruct.core.shared.client.Client;
import com.obstruct.core.shared.client.ClientDataRepository;
import com.obstruct.core.shared.client.Rank;
import com.obstruct.core.shared.redis.RedisManager;
import com.obstruct.core.spigot.framework.SpigotModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener extends SpigotModule<ClanManager> implements Listener {

    public ChatListener(ClanManager manager) {
        super(manager, "ChatListener");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }
        event.setCancelled(true);
        Player player = event.getPlayer();
        String message = event.getMessage();
        Clan clan = getManager().getClan(player);
        if (getManager().getChatType(player.getUniqueId()) == ChatType.CLAN) {
            clan.inform(false, null, ChatColor.AQUA + player.getName() + " " + ChatColor.DARK_AQUA + message);
            return;
        }
        if (getManager().getChatType(player.getUniqueId()) == ChatType.ALLY) {
            clan.inform(false, null, ChatColor.DARK_GREEN + clan.getName() + " " + player.getName() + " " + ChatColor.GREEN + message);
            for (String s : clan.getAlliance().keySet()) {
                getManager().getClan(s).inform(false, null, ChatColor.DARK_GREEN + clan.getName() + " " + player.getName() + " " + ChatColor.GREEN + message);
            }
            return;
        }
        Client client = getManager(RedisManager.class).getModule(ClientDataRepository.class).getClient(player.getUniqueId());
        if (client == null) {
            return;
        }
        for (Player online : Bukkit.getOnlinePlayers()) {
            //if client ignore contains online then continue;
            Clan target = getManager(ClanManager.class).getClan(online);
            String rank = "";
            if (client.hasRank(Rank.MEDIA, false)) {
                rank = client.getRank().getTag(true) + " ";
            }
            if (clan == null) {
                online.sendMessage(rank + ChatColor.YELLOW + player.getName() + ChatColor.RESET + " " + message);
                continue;
            }
            ClanRelation clanRelation = getManager().getClanRelation(clan, target);
            online.sendMessage(rank + clanRelation.getPrefix() + clan.getName() + " " + clanRelation.getSuffix() + player.getName() + ChatColor.RESET + ": " + message);
        }
    }
}