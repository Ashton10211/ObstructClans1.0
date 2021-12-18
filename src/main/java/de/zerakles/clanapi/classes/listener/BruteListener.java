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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;


public class BruteListener {
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

    public HashMap<Player, Integer>slamDelay = new HashMap<>();



    @EventHandler
    public void slam(PlayerInteractEvent playerInteractEvent){
        if(!getManager().hasKit(playerInteractEvent.getPlayer()))
            return;
        if(!getManager().getKit(playerInteractEvent.getPlayer()).equalsIgnoreCase("Brute"))
            return;
        if(!isAxe(playerInteractEvent.getPlayer().getItemInHand()))
            return;
        if(isSilenced(playerInteractEvent.getPlayer()))
            return;
        if(!canUse(playerInteractEvent.getPlayer()))
            return;
        if(slamDelay.containsKey(playerInteractEvent.getPlayer()))
            return;
        Player player = playerInteractEvent.getPlayer();
        for(Player all : Bukkit.getOnlinePlayers()){
            if(all.getLocation().distance(player.getLocation())< 2.5){
                all.setVelocity(new Vector(0D,1.3D,0D));
                all.damage(4);
                wasStamped.add(all);
            }
        }
        slamDelay.put(player,10);
        player.sendMessage(getManager().prefix + "§7You used §aslam§7!");
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

    ArrayList<Player>wasStamped = new ArrayList<>();

    @EventHandler
    public void moreD(EntityDamageByEntityEvent entityDamageByEntityEvent){
        if(!(entityDamageByEntityEvent.getEntity() instanceof  Player))
            return;
        if(!(entityDamageByEntityEvent.getDamager() instanceof  Player))
            return;
        Player player = (Player) entityDamageByEntityEvent.getDamager();
        Player sacrum = (Player) entityDamageByEntityEvent.getEntity();
        if(!getManager().hasKit(player))
            return;
        if(!getManager().getKit(player).equalsIgnoreCase("Brute"))
            return;
        if(!wasStamped.contains(sacrum))
            return;
        entityDamageByEntityEvent.setDamage(entityDamageByEntityEvent.getDamage()+3);
        sacrum.setVelocity(sacrum.getVelocity().multiply(1.5));
        return;
    }

    @EventHandler
    public void damageEvent(EntityDamageEvent entityDamageEvent){
        if(!(entityDamageEvent.getEntity() instanceof  Player))
            return;
        Player player = (Player) entityDamageEvent.getEntity();
        if(!getManager().hasKit(player))
            return;
        if(!getManager().getKit(player).equalsIgnoreCase("Brute"))
            return;
        entityDamageEvent.setDamage(entityDamageEvent.getDamage()/1.5);
        return;
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

    public void removeSlamDelay(Player player){
        slamDelay.remove(player);
        player.sendMessage(getManager().prefix + "§7Slam is up again!");
    }

    public void loop(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
                for (Player player: slamDelay.keySet()
                     ) {
                    if(slamDelay.get(player) == 0){
                        removeSlamDelay(player);
                        continue;
                    }
                    slamDelay.replace(player, slamDelay.get(player)-1);
                    continue;
                }
            }
        },0,20);
    }
    int tickChecker = 0;
    public void hardLoop(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getClan(), new Runnable() {
            @Override
            public void run() {
                if(tickChecker == 80) {
                    wasStamped.clear();
                    tickChecker = 0;
                }
                tickChecker++;
            }
        },0,1);
    }
}
