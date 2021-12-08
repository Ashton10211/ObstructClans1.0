package de.zerakles.listener;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class JoinEvent implements Listener {

    private Data getData(){
        return Clan.getClan().data;
    }

    private ClanAPI getClanAPI(){
        return Clan.getClan().getClanAPI();
    }

    //record12 => WindBlade

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent){
        playerJoinEvent.getPlayer().setResourcePack(Clan.getClan().data.resourcepack);
        playerJoinEvent.setJoinMessage("§8Join> §7" +  playerJoinEvent.getPlayer().getName());
        Clan.getClan().scoreboardMaster.sendScoreboard(playerJoinEvent.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent playerQuitEvent){
        if(getData().inClanChat.contains(playerQuitEvent.getPlayer())){
            getData().inClanChat.remove(playerQuitEvent.getPlayer());
        }
        if(getData().inAllyChat.contains(playerQuitEvent.getPlayer())){
            getData().inAllyChat.remove(playerQuitEvent.getPlayer());
        }
        playerQuitEvent.setQuitMessage("§8Quit> §7" + playerQuitEvent.getPlayer().getName());
    }

}
