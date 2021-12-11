package de.zerakles.listener;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class ChatListener implements Listener {
    private Data getData(){
        return Clan.getClan().data;
    }
    private ClanAPI getClanAPI(){
        return Clan.getClan().getClanAPI();
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if(getData().inClanChat.contains(event.getPlayer())){
            String clan = getClanAPI().getClan(event.getPlayer().getUniqueId().toString());
            for(Player all: Bukkit.getOnlinePlayers()){
                if(getClanAPI().getClan(all.getUniqueId().toString()).equalsIgnoreCase(clan)){
                    all.sendMessage(getData().prefix + getData().chatFormat.replaceAll("%clan%", clan)
                            .replaceAll("%player%", event.getPlayer().getName())
                            .replaceAll("%message%", event.getMessage()));
                }
            }
            event.setCancelled(true);
            return;
        }
        if(getData().inAllyChat.contains(event.getPlayer())){
            String clan = getClanAPI().getClan(event.getPlayer().getUniqueId().toString());
            String AllyClan = getData().allyClans.get(clan);
            for(Player all: Bukkit.getOnlinePlayers()){
                if(getClanAPI().getClan(all.getUniqueId().toString()).equalsIgnoreCase(clan)){
                    all.sendMessage(getData().prefix + getData().chatFormat.replaceAll("%clan%", clan)
                            .replaceAll("%player%", event.getPlayer().getName())
                            .replaceAll("%message%", event.getMessage()));
                    continue;
                }
                if(getClanAPI().getClan(all.getUniqueId().toString()).equalsIgnoreCase(AllyClan)){
                    all.sendMessage(getData().prefix + getData().chatFormat.replaceAll("%clan%", clan)
                            .replaceAll("%player%", event.getPlayer().getName())
                            .replaceAll("%message%", event.getMessage()));
                    continue;
                }
            }
            event.setCancelled(true);
            return;
        }
        if(getClanAPI().playerExists(event.getPlayer().getUniqueId().toString())){
            String clan = getClanAPI().getClan(event.getPlayer().getUniqueId().toString());
            String message = getData().chatFormat;
            message = message.replaceAll("%player%", event.getPlayer().getName());
            message = message.replaceAll("%clan%", clan);
            message = message.replaceAll("%message%", event.getMessage());
            for(Player all : Bukkit.getOnlinePlayers()){
                all.sendMessage(message);
            }
            event.setCancelled(true);
            return;
        }
        event.setFormat("§e" + event.getPlayer().getName() + "§8: §f" + event.getMessage());
        return;
    }
}
