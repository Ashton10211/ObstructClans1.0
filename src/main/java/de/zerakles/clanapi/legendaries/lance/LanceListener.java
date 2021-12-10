package de.zerakles.clanapi.legendaries.lance;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import de.zerakles.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class LanceListener implements Listener {
    private Clan getClan(){
        return Clan.getClan();
    }
    private ClanAPI getClanAPI(){
        return getClan().getClanAPI();
    }
    private Data getData(){
        return getClan().data;
    }

    public HashMap<Player, Legend> Lances = new HashMap<>();

    public LanceListener(){
        loop();
    }

    public void loop(){}

    private boolean isCorrectItem(ItemStack item, Player p) {
        if(Lances.containsKey(p)){
            if(!item.hasItemMeta()){
                return false;
            }
            if(item.getItemMeta().equals(Lances.get(p).getItemStack().getItemMeta())){
                return true;
            }
            return false;
        }
        return false;
    }

    ArrayList<Player>firstHit = new ArrayList<>();
    HashMap<Player, Long> stunnedPlayer = new HashMap<>();

    @EventHandler
    public void onDmg(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player)e.getDamager();
            ItemStack item = Utils.getItemInHand(p);
            if (isCorrectItem(item,p)) {
                e.setDamage(Lances.get(p).getDamage());
                if(!(e.getEntity() instanceof Player)){
                    return;
                }
                if(firstHit.contains(p)){
                    stunnedPlayer.put((Player) e.getEntity(), System.currentTimeMillis());
                    e.getEntity().setFireTicks(20*3);
                    p.sendMessage(getData().prefix + "§7Player stunned!");
                    firstHit.remove(p);
                } else {
                    firstHit.add(p);
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent playerMoveEvent){
        if(stunnedPlayer.containsKey(playerMoveEvent.getPlayer())){
            if(System.currentTimeMillis()-stunnedPlayer.get(playerMoveEvent.getPlayer()) == 1L
                ||  System.currentTimeMillis()-stunnedPlayer.get(playerMoveEvent.getPlayer()) / 1000L > 0.8){
                stunnedPlayer.remove(playerMoveEvent.getPlayer());
                return;
            }
            playerMoveEvent.setCancelled(true);
            playerMoveEvent.getPlayer().sendMessage(getData().prefix + "§7You are stunned!");
            return;
        }
    }
}
