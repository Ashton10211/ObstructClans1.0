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

    public HashMap<Player, Legend> AlligatorThooths = new HashMap<>();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent playerItemDamageEvent){
        if(!(playerItemDamageEvent.getDamager() instanceof Player))
            return;
        Player player = (Player) playerItemDamageEvent.getDamager();
        if(player.getItemInHand().getType() == Material.RECORD_4){
            if(player.getItemInHand().getItemMeta().hasDisplayName()){
                if(player.getItemInHand().getItemMeta().getDisplayName().
                        equalsIgnoreCase("§e§lAlligators Tooth")){
                    if(!AlligatorThooths.containsKey(player)){
                        return;
                    }
                    if(!player.getItemInHand().getItemMeta().getLore().contains("§8"
                            + AlligatorThooths.get(player).getUuid())){
                        return;
                    }
                    playerItemDamageEvent.setDamage(AlligatorThooths.get(player).getDamage());
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent playerInteractEvent){
        Player player = playerInteractEvent.getPlayer();
        ItemStack itemStack = playerInteractEvent.getPlayer().getItemInHand();
        if(!AlligatorThooths.containsKey(playerInteractEvent.getPlayer())){
            return;
        }
        if(!playerInteractEvent.getItem().getItemMeta().getLore().contains("§8"
                + AlligatorThooths.get(playerInteractEvent.getPlayer()).getUuid())){
            return;
        }
        if(itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(AlligatorThooths.get(playerInteractEvent.getPlayer())
                .getItemStack().getItemMeta().getDisplayName())){
            if(!itemStack.getItemMeta().getLore().contains("§8" + AlligatorThooths.get(playerInteractEvent.getPlayer())
                    .getUuid())){
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
    }

    public void loop() throws NullPointerException{
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
        for(Player all : Bukkit.getOnlinePlayers()){
            ItemStack itemStack = all.getItemInHand();

            if(!AlligatorThooths.containsKey(all.getPlayer())){
                return;
            }
            if(itemStack == null){
                return;
            }
            if(!itemStack.hasItemMeta())
                return;
            if(!itemStack.getItemMeta().hasLore())
                return;
            if(!itemStack.getItemMeta().getLore().contains("§8" + AlligatorThooths.get(all).getUuid())){
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
