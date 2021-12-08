package de.zerakles.listener;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import de.zerakles.utils.ZoneTypes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class MovementListener implements Listener {

    private Data getData(){
        return Clan.getClan().data;
    }

    private ClanAPI getClanAPI(){
        return Clan.getClan().getClanAPI();
    }
    HashMap<Player, Location> lovs = new HashMap<Player, Location>();
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        if(lovs.containsKey(event.getPlayer())) {
            if (event.getTo().distance(lovs.get(event.getPlayer())) < 2) {
                return;
            }
        }
        if(getClanAPI().getZone(event.getTo(), event.getPlayer())
                != getData().zones.get(event.getPlayer())){
            Bukkit.getConsoleSender().sendMessage(getData().prefix + "§aChanged Zones §e"
                    + getClanAPI().getZone(event.getTo(), event.getPlayer())
                    + " §5" + event.getPlayer().getName());
            ZoneTypes oldZone;
            if(lovs.containsKey(event.getPlayer())){
                lovs.remove(event.getPlayer());
            }
            lovs.put(event.getPlayer(), event.getTo());
            if(getData().zones.get(event.getPlayer())==null){
                oldZone = ZoneTypes.SPAWN;
            }else{
                oldZone = getData().zones.get(event.getPlayer());
            }
            getData().zones.remove(event.getPlayer());
            getData().zones.put(event.getPlayer(), getClanAPI().getZone(event.getTo(), event.getPlayer()));
            ZoneTypes newZone = getData().zones.get(event.getPlayer());
            if(newZone == ZoneTypes.CLAN){
                String clan = getClanAPI().getClan(event.getPlayer().getUniqueId().toString());
                event.getPlayer().sendTitle("§b§l" + clan.toUpperCase(),"");
            } else if(newZone == ZoneTypes.ENEMYCLAN){
                String clan = getClanAPI().getChunkOwner(event.getTo());
                event.getPlayer().sendTitle("§c§l" + clan.toUpperCase(),"");
            } else if(newZone == ZoneTypes.SPAWN){
                event.getPlayer().sendTitle("§b§l" + newZone,"");
            } else if(newZone == ZoneTypes.SHOP){
                event.getPlayer().sendTitle("§b§l" + newZone,"");
            } else {
                event.getPlayer().sendTitle("§e§l" + newZone,"");
            }
            return;
        }
    }

}
