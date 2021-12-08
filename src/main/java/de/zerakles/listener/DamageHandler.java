package de.zerakles.listener;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import de.zerakles.utils.ZoneTypes;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageHandler implements Listener {
    private Data getData(){
        return Clan.getClan().data;
    }
    private ClanAPI getClanAPI(){
        return Clan.getClan().getClanAPI();
    }
    @EventHandler
    public void onDamage(EntityDamageByBlockEvent entityDamageByBlockEvent){
        if(entityDamageByBlockEvent.getEntity() instanceof Player){
            if(getClanAPI().getZone(entityDamageByBlockEvent.getEntity().getLocation(),
                    (Player) entityDamageByBlockEvent.getEntity()) == ZoneTypes.SPAWN){
                entityDamageByBlockEvent.setCancelled(true);
            }
        }
    }

    @EventHandler
    public  void onDamageByEntity(EntityDamageByEntityEvent entityDamageByEntityEvent){
        if(entityDamageByEntityEvent.getEntity() instanceof Villager){
            if(getClanAPI().getZone(entityDamageByEntityEvent.getEntity().getLocation(),
                    entityDamageByEntityEvent.getEntity()) == ZoneTypes.SPAWN ||
                    getClanAPI().getZone(entityDamageByEntityEvent.getEntity().getLocation(),
                            entityDamageByEntityEvent.getEntity()) == ZoneTypes.SHOP ){
                entityDamageByEntityEvent.setCancelled(true);
                return;
            }
        }
        if(entityDamageByEntityEvent.getEntity() instanceof Player){
            if(getClanAPI().getZone(entityDamageByEntityEvent.getEntity().getLocation(),
                    (Player) entityDamageByEntityEvent.getEntity()) == ZoneTypes.SPAWN ||
                    getClanAPI().getZone(entityDamageByEntityEvent.getEntity().getLocation(),
                    (Player) entityDamageByEntityEvent.getEntity()) == ZoneTypes.SHOP ){
                entityDamageByEntityEvent.setCancelled(true);
                return;
            }
            if(!getClanAPI().playerExists(entityDamageByEntityEvent.getEntity().getUniqueId().toString())){
                return;
            }
            Player player = ((Player) entityDamageByEntityEvent.getEntity()).getPlayer();
            String clan = getClanAPI().getClan(player.getUniqueId().toString());
            if(entityDamageByEntityEvent.getDamager() instanceof  Player){
                Player attacker = (Player) entityDamageByEntityEvent.getDamager();
                if(!getClanAPI().playerExists(attacker.getUniqueId().toString())){
                    return;
                }
                String attackerClan = getClanAPI().getClan(attacker.getUniqueId().toString());
                if(clan.equalsIgnoreCase(attackerClan)){
                    entityDamageByEntityEvent.setCancelled(true);
                    return;
                }
                if(getData().allyClans.containsKey(clan)){
                    if(getData().allyClans.get(clan).equalsIgnoreCase(attackerClan)){
                        entityDamageByEntityEvent.setCancelled(true);
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
    }

}
