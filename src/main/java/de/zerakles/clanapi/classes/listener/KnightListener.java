package de.zerakles.clanapi.classes.listener;

import de.zerakles.clanapi.ClanAPI;
import de.zerakles.clanapi.classes.Manager;
import de.zerakles.clanapi.classes.effects.EnumEffect;
import de.zerakles.main.Clan;
import de.zerakles.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class KnightListener {
    /*

            IMPORTS


     */
    private ClanAPI getClanAPI() {
        return Clan.getClan().getClanAPI();
    }

    private Data getData() {
        return Clan.getClan().data;
    }

    private Manager getManager() {
        return Clan.getClan().manager;
    }

    private Clan getClan() {
        return Clan.getClan();
    }

    private boolean canUse(Player player) {
        if (player.getLocation().getBlock().isLiquid()) {
            player.sendMessage(getManager().prefix + "§7You can't use this skill in water!");
            return false;
        }
        return true;
    }

    public boolean isSilenced(Player player) {
        if (getManager().effectManager.hasEffect(player, EnumEffect.SILENCED)) {
            return true;
        }
        return false;
    }
    public boolean isSword(ItemStack itemStack) {
        if ( (itemStack.getType().equals(Material.WOOD_SWORD))
                || (itemStack.getType().equals(Material.STONE_SWORD))
                || (itemStack.getType().equals(Material.IRON_SWORD))
                || (itemStack.getType().equals(Material.GOLD_SWORD))
                || (itemStack.getType().equals(Material.DIAMOND_SWORD))) {
            return true;
        }
        return false;
    }

    public boolean isAxeOrSword(ItemStack itemStack) {
        if ((itemStack.getType().equals(Material.WOOD_AXE)) || (itemStack.getType().equals(Material.WOOD_SWORD))
                || (itemStack.getType().equals(Material.STONE_AXE)) || (itemStack.getType().equals(Material.STONE_SWORD))
                || (itemStack.getType().equals(Material.IRON_AXE)) || (itemStack.getType().equals(Material.IRON_SWORD))
                || (itemStack.getType().equals(Material.GOLD_SWORD)) || (itemStack.getType().equals(Material.GOLD_AXE))
                || (itemStack.getType().equals(Material.DIAMOND_AXE)) || (itemStack.getType().equals(Material.DIAMOND_SWORD))) {
            return true;
        }
        return false;
    }

    public boolean isAxe(ItemStack itemStack) {
        if ((itemStack.getType().equals(Material.WOOD_AXE))
                || (itemStack.getType().equals(Material.STONE_AXE))
                || (itemStack.getType().equals(Material.IRON_AXE))
                || (itemStack.getType().equals(Material.GOLD_AXE))
                || (itemStack.getType().equals(Material.DIAMOND_AXE))) {
            return true;
        }
        return false;
    }

    public void loop(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
                for(Player player : invulnerableCD.keySet()){
                    if(invulnerableCD.get(player) == 7){
                        invulnerable.remove(player);
                    }
                    if(invulnerableCD.get(player) == 0){
                        removeIn(player);
                    }
                    invulnerableCD.replace(player, invulnerableCD.get(player)-1);
                }
                for(Player player : bullsCD.keySet()){
                    if(bullsCD.get(player) == 0){
                        removeBu(player);
                    }
                    bullsCD.replace(player, bullsCD.get(player)-1);
                }
            }
        },0,20);
    }
    int tickChecker = 0;
    public void hardLoop(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
            }
        },0,1);
    }

    /*


            CODE STARTS HERE:


     */

    HashMap<Player, Integer> bullsCD = new HashMap<>();

    @EventHandler
    public void onHit(EntityDamageByEntityEvent entityDamageByEntityEvent){
        if(entityDamageByEntityEvent.getDamager() instanceof  Player){
            if(bullsCD.containsKey((Player) entityDamageByEntityEvent.getDamager())){
                if(entityDamageByEntityEvent.getEntity() instanceof Player){
                    ((Player)entityDamageByEntityEvent.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
                            20,
                            1));
                }
            }
        }
        if(entityDamageByEntityEvent.getEntity() instanceof Player){
            Player player = ((Player) entityDamageByEntityEvent.getEntity()).getPlayer();
            if(!getManager().hasKit(player))
                return;
            if(!getManager().getKit(player).equalsIgnoreCase("Knight"))
                return;
            if(entityDamageByEntityEvent.getDamager() instanceof  Player){
                Player d = (Player) entityDamageByEntityEvent.getDamager();
                d.damage(2);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent playerInteractEvent){
        Player player = playerInteractEvent.getPlayer();
        if(!getManager().hasKit(player))
            return;
        if(!getManager().getKit(player).equalsIgnoreCase("Knight"))
            return;
        if(playerInteractEvent.getAction() != Action.RIGHT_CLICK_BLOCK || playerInteractEvent.getAction() != Action.RIGHT_CLICK_AIR)
            return;
        if(isSilenced(player))
            return;
        if(!canUse(player))
            return;
        if(!bullsCD.containsKey(player))
            return;
        bullsCD.put(player,10);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*5, 9));
        player.sendMessage(getManager().prefix + "§7You used BullsCharge!");
    }

    public void removeBu(Player player){
        player.sendMessage(getManager().prefix + "§7BullCharge back online!");
        bullsCD.remove(player);
    }



    HashMap<Player, Integer>invulnerableCD = new HashMap<>();
    ArrayList<Player>invulnerable=new ArrayList<>();

    public void removeIn(Player player){
        invulnerableCD.remove(player);
        player.sendMessage(getManager().prefix + "§7Invulnerable ability is back online!");
    }

    @EventHandler
    public void onDMG(EntityDamageEvent entityDamageEvent){
        if(entityDamageEvent.getEntity()instanceof Player){
            if(invulnerable.contains((Player) entityDamageEvent.getEntity())){
                entityDamageEvent.setCancelled(true);
                entityDamageEvent.setDamage(0);
            }
        }
    }

    @EventHandler
    public void onInvulnerable(PlayerInteractEvent playerInteractEvent){
        Player player = playerInteractEvent.getPlayer();
        if(!getManager().hasKit(player))
            return;
        if(!getManager().getKit(player).equalsIgnoreCase("Knight"))
            return;
        if(isSilenced(player))
            return;
        if(!canUse(player))
            return;
        if(!invulnerableCD.containsKey(player))
            return;
        invulnerableCD.put(player, 10);
        invulnerable.add(player);
        player.sendMessage(getManager().prefix + "§7You are §ainvulnerable §7for 4secs");
        return;
    }
}
