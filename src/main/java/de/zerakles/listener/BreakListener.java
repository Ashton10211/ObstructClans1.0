package de.zerakles.listener;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import de.zerakles.utils.ZoneTypes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class BreakListener implements Listener {

    private ClanAPI getClanAPI(){
        return Clan.getClan().getClanAPI();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent blockBreakEvent){
        Player player = blockBreakEvent.getPlayer();
        if(getClanAPI().cantDestroy.contains(blockBreakEvent.getBlock())){
            blockBreakEvent.setCancelled(true);
            return;
        }
        if(getClanAPI().getZone(blockBreakEvent.getBlock().getLocation(), player) == ZoneTypes.ENEMYCLAN ||
                getClanAPI().getZone(blockBreakEvent.getBlock().getLocation(), player) == ZoneTypes.SPAWN ||
                getClanAPI().getZone(blockBreakEvent.getBlock().getLocation(), player) == ZoneTypes.SHOP){
            blockBreakEvent.setCancelled(true);
            return;
        }
        return;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent blockPlaceEvent){
        Player player = blockPlaceEvent.getPlayer();
        if(getClanAPI().getZone(blockPlaceEvent.getBlock().getLocation(), player) == ZoneTypes.ENEMYCLAN ||
                getClanAPI().getZone(blockPlaceEvent.getBlock().getLocation(), player) == ZoneTypes.SPAWN ||
                getClanAPI().getZone(blockPlaceEvent.getBlock().getLocation(), player) == ZoneTypes.SHOP){
            blockPlaceEvent.setCancelled(true);
            return;
        }
        return;
    }

    private Data getData(){
       return Clan.getClan().data;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent playerDeathEvent){
        Player player = playerDeathEvent.getEntity();
        if(player.getKiller() != null){
            if(player.getKiller() instanceof Player){
                playerDeathEvent.setDeathMessage(getData().prefix + getData().playerKilledByPlayer
                        .replaceAll("%player%", player.getName())
                        .replaceAll("%killer%", player.getKiller().getName()));
                return;
            }
        }
        playerDeathEvent.setDeathMessage(getData().prefix + getData().playerDeath
                .replaceAll("%player%", player.getName()));
        return;
    }

    @EventHandler
    public void onSpawnEntity(EntitySpawnEvent entitySpawnEvent){
        if(entitySpawnEvent.getEntity() instanceof Player) {
            return;
        }
        if(entitySpawnEvent.getEntity() instanceof Villager){
            return;
        }
        if(getClanAPI().getZone(entitySpawnEvent.getLocation(), entitySpawnEvent.getEntity()) == ZoneTypes.SPAWN ||
                getClanAPI().getZone(entitySpawnEvent.getLocation(), entitySpawnEvent.getEntity()) == ZoneTypes.SHOP ){
            entitySpawnEvent.setCancelled(true);
            return;
        }
    }
}
