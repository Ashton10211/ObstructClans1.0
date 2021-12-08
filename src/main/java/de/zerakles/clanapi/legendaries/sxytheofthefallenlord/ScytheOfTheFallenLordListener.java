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

    public HashMap<Player, Legend> ScytheOfs = new HashMap<>();

    public ScytheOfTheFallenLordListener(){
        loop();
    }

    public void loop(){}

    private boolean isCorrectItem(ItemStack item, Player p) {
        if(ScytheOfs.containsKey(p)){
            if(!item.hasItemMeta()){
                return false;
            }
            if(item.getItemMeta().equals(ScytheOfs.get(p).getItemStack().getItemMeta())){
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
                e.setDamage(ScytheOfs.get(p).getDamage());
                p.setHealth(Math.min(20.0D, p.getHealth() + 2.0D));
            }
        }
    }

}
