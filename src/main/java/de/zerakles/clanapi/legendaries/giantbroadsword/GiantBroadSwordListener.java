package de.zerakles.clanapi.legendaries.giantbroadsword;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class GiantBroadSwordListener implements Listener {
    private Clan getClan(){
        return Clan.getClan();
    }
    private ClanAPI getClanAPI(){
        return getClan().getClanAPI();
    }
    private Data getData(){
        return getClan().data;
    }

    private ArrayList<Player> noHits = new ArrayList<>();

    public GiantBroadSwordListener(){
        loop();
    }

    @EventHandler
    public void onHitWithSword(PlayerItemDamageEvent playerItemDamageEvent){
        if(GiantBroadSwords.containsKey(playerItemDamageEvent.getPlayer())){
            if (playerItemDamageEvent.getItem().getItemMeta().getDisplayName()
                    .equalsIgnoreCase(GiantBroadSwords.get(playerItemDamageEvent.getPlayer()).getName())){
                if(playerItemDamageEvent.getItem().getItemMeta().getLore()
                        .contains("§8" + GiantBroadSwords.get(playerItemDamageEvent.getPlayer()).getUuid())){
                    playerItemDamageEvent.setDamage(10);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onDmg(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player)e.getDamager();
            ItemStack item = p.getItemInHand();
            if (noHits.contains(p.getName()))
                e.setCancelled(true);
            if(!GiantBroadSwords.containsKey(p))
                return;
            if (item.getItemMeta().equals(GiantBroadSwords.get(p).getItemStack().getItemMeta()))
                e.setDamage(GiantBroadSwords.get(p).getDamage());
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();
        if(!GiantBroadSwords.containsKey(p)){
            return;
        }
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) &&
                GiantBroadSwords.get(p).getItemStack().getItemMeta().equals(item.getItemMeta())) {
            noHits.add(p);
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 35, 3));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 35, 255));
            if (p != null && p.isOnline())
                p.playSound(p.getLocation(), Sound.valueOf("LAVA_POP"), 13.0F, 2.0F);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getClan(), () -> {
                if (p != null && p.isOnline())
                    p.playSound(p.getLocation(), Sound.valueOf("LAVA_POP"), 13.0F, 2.0F);
            },3L);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getClan(), () -> {
                if (p != null && p.isOnline())
                    p.playSound(p.getLocation(), Sound.valueOf("LAVA_POP"), 13.0F, 2.0F);
                noHits.remove(p);
            },5L);
            Location block = p.getLocation().clone().add(0.0D, 2.0D, 0.0D);
            double x = block.getX();
            double y = block.getY();
            double z = block.getZ();
            for (Player pla : Bukkit.getServer().getOnlinePlayers()) {
                if (pla.getLocation().distance(new Location(pla.getWorld(), x, y, z)) > 64.0D)
                    return;
                pla.playEffect(pla.getLocation(), Effect.HEART, 2);
            }
        }
    }

    public HashMap<Player, Legend> GiantBroadSwords= new HashMap<>();


    public void loop(){
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
                for(Player all : Bukkit.getOnlinePlayers()){
                    ItemStack item = all.getItemInHand();
                    if (all.isDead())
                        return;
                    if(GiantBroadSwords.containsKey(all)){
                        if(GiantBroadSwords.get(all).getItemStack().equals(item)){
                            all.playEffect(all.getLocation(),Effect.MAGIC_CRIT,2);
                        }
                    }
                }
            }
        },0,1);
    }
}
