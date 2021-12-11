package de.zerakles.clanapi.legendaries.alligatorstooth;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.legendaries.Legend;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class AlligatorsToothListener implements Listener {

    private  Clan getClan(){
        return Clan.getClan();
    }
    private ClanAPI getClanAPI(){
        return getClan().getClanAPI();
    }
    private Data getData(){
        return getClan().data;
    }

    public AlligatorsToothListener(){
        loop();
    }

    public HashMap<Legend, Player> AlligatorThooths = new HashMap<>();
    public int getDamage(){
        if(AlligatorThooths.size() > 0){
            for (Legend legend:AlligatorThooths.keySet()
            ) {
                return legend.getDamage();
            }
        }
        return 0;
    }

    public boolean checkItemInHand(ItemStack itemStack, Player player){
        for (Legend legend: AlligatorThooths.keySet()
        ) {
            if(itemStack.getItemMeta().getLore().contains("§8" + legend.getUuid())){
                return true;
            }
            continue;
        }
        return false;
    }

    public  boolean haveLegendary(Player player){
        for (Legend legend: AlligatorThooths.keySet()
        ) {
            if(AlligatorThooths.get(legend).getUniqueId().toString()
                    .equals(player.getUniqueId().toString())){
                return true;
            }
            continue;
        }
        return false;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent playerItemDamageEvent){
        if(!(playerItemDamageEvent.getDamager() instanceof Player))
            return;
        Player player = (Player) playerItemDamageEvent.getDamager();
        if(player.getItemInHand().getType() == Material.RECORD_4){
            if(player.getItemInHand().getItemMeta().hasDisplayName()){
                if(player.getItemInHand().getItemMeta().getDisplayName().
                        equalsIgnoreCase("§e§lAlligators Tooth")){
                    if(!haveLegendary(player)){
                        return;
                    }
                    if(!checkItemInHand(player.getItemInHand(), player)){
                        return;
                    }
                    playerItemDamageEvent.setDamage(getDamage());
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent playerInteractEvent){
        Player player = playerInteractEvent.getPlayer();
        if(!haveLegendary(player)){
            return;
        }
        if(!checkItemInHand(player.getItemInHand(), player)){
            return;
        }
        if(playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK ||
                playerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR){
            if (player.getLocation().getBlock().getType() == Material.WATER ||
                    player.getLocation().getBlock().getType() == Material.STATIONARY_WATER) {
                player.setVelocity(player.getLocation().getDirection().multiply(1.3D));
                Block block = player.getLocation().getBlock();
                block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, 8);
                block.getWorld().playSound(block.getLocation(), Sound.SPLASH, 0.4F, 1.0F);
            }
        }

    }

    public void loop() throws NullPointerException{
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
        for(Player all : Bukkit.getOnlinePlayers()){
            ItemStack itemStack = all.getItemInHand();

            if(!haveLegendary(all)){
                return;
            }
            if(itemStack == null){
                return;
            }
            if(!itemStack.hasItemMeta())
                return;
            if(!itemStack.getItemMeta().hasLore())
                return;
            if(!checkItemInHand(itemStack, all)){
                all.removePotionEffect(PotionEffectType.WATER_BREATHING);
                return;
            }
            if(all.hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
                all.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 99999999, 255));
                return;
            }
        }
            }
        },0,1);
    }

}
