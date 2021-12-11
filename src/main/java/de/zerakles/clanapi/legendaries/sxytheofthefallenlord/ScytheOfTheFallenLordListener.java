package de.zerakles.clanapi.legendaries.sxytheofthefallenlord;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import de.zerakles.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ScytheOfTheFallenLordListener implements Listener {

    private Clan getClan(){
        return Clan.getClan();
    }
    private ClanAPI getClanAPI(){
        return getClan().getClanAPI();
    }
    private Data getData(){
        return getClan().data;
    }

    public HashMap<Legend, Player> ScytheOfs = new HashMap<>();

    public int getDamage(){
        if(ScytheOfs.size() > 0){
            for (Legend legend:ScytheOfs.keySet()
            ) {
                return legend.getDamage();
            }
        }
        return 0;
    }

    public boolean checkItemInHand(ItemStack itemStack, Player player){
        for (Legend legend: ScytheOfs.keySet()
        ) {
            if(itemStack.getItemMeta().getLore().contains("§8" + legend.getUuid())){
                return true;
            }
            continue;
        }
        return false;
    }

    public  boolean haveLegendary(Player player){
        for (Legend legend: ScytheOfs.keySet()
        ) {
            if(ScytheOfs.get(legend).getUniqueId().toString()
                    .equals(player.getUniqueId().toString())){
                return true;
            }
            continue;
        }
        return false;
    }

    public ScytheOfTheFallenLordListener(){
        loop();
    }

    public void loop(){}

    private boolean isCorrectItem(ItemStack item, Player p) {
        if(haveLegendary(p)){
            if(!item.hasItemMeta()){
                return false;
            }
            if(checkItemInHand(item, p)){
                return true;
            }
            return false;
        }
        return false;
    }

    @EventHandler
    public void onDmg(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player)e.getDamager();
            ItemStack item = Utils.getItemInHand(p);
            if (isCorrectItem(item,p)) {
                e.setDamage(getDamage());
                p.setHealth(Math.min(20.0D, p.getHealth() + 2.0D));
            }
        }
    }

}
